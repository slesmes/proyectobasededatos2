create sequence id_artista
	start with 1
	increment by 1;

create sequence id_redsocial
	start with 1
	increment by 1;

create sequence id_lugar
	start with 1
	increment by 1;

create sequence id_evento
	start with 1
	increment by 1;

create sequence id_evento_detallado
	start with 1
	increment by 1;

create sequence id_cliente
	start with 1
	increment by 1;

create sequence id_asiento
	start with 1
	increment by 1;

create sequence codigo_asiento
	start with 100
	increment by 1;

create sequence codigo_inventario
	start with 1
	increment by 1;



--------------------fin secuencias---------------------------

create table artista(
	id varchar primary key,
	nombre varchar,
	genero_musical varchar check(genero_musical in ('salsa','rock','pop','reguetton'))	
)

create table redsocial(
	id varchar primary key,
	nombre varchar check(nombre in ('instagram','facebook','twitter')),
	usuario varchar,
	artista_id varchar,
	
	foreign key (artista_id) references artista(id)
)

create table lugar(
	id varchar primary key,
	nombre varchar,
	direccion varchar,
	capacidad int,
	ciudad varchar,
	imagen varchar
)

create table evento(
	id varchar primary key,
	nombre varchar,
	fecha date,
	hora time,
	descripcion varchar,
	genero_musical varchar check (genero_musical in ('salsa','rock','pop','reguetton')),
	estado varchar check (estado in ('programado','cancelado','finalizado')),
	cartel varchar,
	lugar_id varchar,
	
	foreign key (lugar_id) references lugar(id)
)

create table evento_detallado(
	id varchar primary key,
	id_evento varchar,
	id_artista varchar,
	
	foreign key (id_evento) references evento(id),
	foreign key (id_artista) references artista(id)
)

create table cliente(
	id varchar primary key,
	nombre varchar,
	email varchar,
	telefono varchar,
	direccion varchar
)

create table asiento(
	id varchar primary key,
	codigo varchar,
	fila varchar,
	columna varchar,
	precio numeric,
	descuento numeric,
	tipo varchar check (tipo in ('general', 'vip', 'palco')),
	estado varchar check (estado in ('disponible', 'reservado', 'vendido')),
	id_lugar varchar,
	
	foreign key (id_lugar) references lugar(id)
)

create table inventario(
	id varchar primary key,
	cantidad_disponible numeric,
	cantidad_vendidos numeric,
	cantidad_reservados numeric,
	id_lugar varchar,
	id_evento varchar,
	
	foreign key(id_lugar) references lugar(id),
	foreign key(id_evento) references evento(id)
)

create table ticket(
	id varchar primary key,
	fecha_compra date,
	descuento numeric,
	precio numeric,
	precio_descuento numeric,
	id_asiento varchar,
	id_cliente varchar,
	id_evento varchar,
	
	foreign key(id_asiento) references asiento(id),
	foreign key(id_cliente) references cliente(id),
	foreign key(id_evento) references evento(id)
)

create table metodo_pago(
	id varchar primary key,
	tipo_pago check (tipo_pago in ('efectivo', 'efectivo y tarjeta de credito', 'efectivo y tarjeta de credito conciertosya', 'tarjeta de credito y tarjeta conciertosya')
)

create table factura(
	id varchar primary key,
	fecha_emision date,
	total numeric,
	id_metodo_pago varchar,
	id_cliente varchar,
	detalle xml,
	foreign key (id_cliente) references cliente(id),
	foreign key (id_metodo_pago) references metodo_pago(id)
)

CREATE TABLE ocupacion_asientos (
    id_asiento VARCHAR,
    id_evento VARCHAR,
    estado VARCHAR CHECK (estado IN ('disponible', 'reservado', 'vendido')),
    PRIMARY KEY (id_asiento, id_evento),
    FOREIGN KEY (id_asiento) REFERENCES asiento(id),
    FOREIGN KEY (id_evento) REFERENCES evento(id)
);

----- fin creacion de tablas ---------------------------------------

create or replace PROCEDURE insertar_artistas()
language plpgsql as $$
DECLARE 
    generos text[] := array ['salsa','rock','pop','reguetton'];
BEGIN
    for i in 1..10 loop
        INSERT INTO artista (id, nombre, genero_musical)
        VALUES (
            nextval('id_artista'),
            CONCAT('Artista_', i),
            generos[TRUNC(RANDOM() * 4 + 1)::INT]
        );
    END loop;
end $$;

call insertar_artistas();
select * from artista;


CREATE OR REPLACE PROCEDURE insertar_redes_sociales()
LANGUAGE plpgsql AS $$
DECLARE
    redes TEXT[] := ARRAY['instagram', 'facebook', 'twitter'];
	id_artista_v varchar;
    artista_cursor cursor for
        select id from artista;
BEGIN
    open artista_cursor;
	loop
		fetch artista_cursor into id_artista_v;
        exit when not found;
        INSERT INTO redsocial (id, nombre, usuario, artista_id)
        VALUES (
            nextval('id_redsocial'),
            redes[TRUNC(RANDOM() * 3 + 1)::INT],
            CONCAT('usuario_', id_artista_v),
            id_artista_v
        );
    END LOOP;
	close artista_cursor;
END $$;

call insertar_redes_sociales();
select * from redsocial;


CREATE OR REPLACE PROCEDURE insertar_lugares()
LANGUAGE plpgsql AS $$
DECLARE
    ciudades TEXT[] := ARRAY['Bogotá', 'Medellín', 'Cali', 'Cartagena', 'Barranquilla'];
BEGIN
    FOR i IN 1..10 LOOP
        INSERT INTO lugar (id, nombre, direccion, capacidad, ciudad, imagen)
        VALUES (
            nextval('id_lugar'),
            CONCAT('Lugar_', i),
            CONCAT('Calle ', i, ' #', i*10, '-', i),
            (50 + (RANDOM() * 450))::INT, 
            ciudades[TRUNC(RANDOM() * array_length(ciudades, 1) + 1)::INT],
            CONCAT('imagen_lugar_', i, '.jpg') 
        );
    END LOOP;
END $$;

call insertar_lugares();
select * from lugar;


CREATE OR REPLACE PROCEDURE insertar_eventos()
LANGUAGE plpgsql AS $$
DECLARE
    generos TEXT[] := ARRAY['salsa', 'rock', 'pop', 'reguetton']; 
    estados TEXT[] := ARRAY['programado', 'cancelado', 'finalizado'];
	id_lugar_v varchar;
	nombre_lugar_v varchar;
    lugar_cursor cursor for
        select id, nombre from lugar;
BEGIN
    open lugar_cursor;
    loop
        fetch lugar_cursor into id_lugar_v, nombre_lugar_v;
		exit when not found;
        FOR i IN 1..2 LOOP 
            INSERT INTO evento (id, nombre, fecha, hora, descripcion, genero_musical, estado, cartel, lugar_id)
            VALUES (
                nextval('id_evento'),
                CONCAT('Evento_', i, '_', id_lugar_v),
                CURRENT_DATE + (i * INTERVAL '1 day'),
                '19:00:00'::TIME + (i * INTERVAL '1 hour'),
                CONCAT('Descripción del evento ', i, ' en lugar ', nombre_lugar_v), 
                generos[TRUNC(RANDOM() * array_length(generos, 1) + 1)::INT],
                estados[TRUNC(RANDOM() * array_length(estados, 1) + 1)::INT],
                CONCAT('cartel_evento_', i, '_', id_lugar_v, '.jpg'),
                id_lugar_v
            );
        END LOOP;
    END LOOP;
	close lugar_cursor;
END $$;

call insertar_eventos();
select * from evento;


CREATE OR REPLACE PROCEDURE insertar_evento_detallado()
LANGUAGE plpgsql AS $$
DECLARE
    evento_cursor cursor for
        select id, genero_musical from evento;
	id_evento_v varchar;
	genero_musical_v varchar;
	artistas RECORD;
BEGIN
    open evento_cursor;
	loop
		fetch evento_cursor into id_evento_V, genero_musical_v;
		exit when not found;
        FOR artistas IN SELECT * FROM artista where artista.genero_musical = genero_musical_v ORDER BY RANDOM() LIMIT 3 LOOP
		--miramos que el genero del artista sea el mismo del evento
            INSERT INTO evento_detallado (id, id_evento, id_artista)
            VALUES (
                nextval('id_evento_detallado'), 
                id_evento_v,
                artistas.id
            );
        END LOOP;
    END LOOP;
	close evento_cursor;
END $$;

call insertar_evento_detallado();
select * from evento_detallado;


CREATE OR REPLACE PROCEDURE insertar_clientes()
LANGUAGE plpgsql AS $$
DECLARE
    nombres TEXT[] := ARRAY['Juan', 'María', 'Carlos', 'Ana', 'Luis', 'Sofía', 'Pedro', 'Lucía', 'Miguel', 'Valentina'];
    apellidos TEXT[] := ARRAY['Pérez', 'Gómez', 'Rodríguez', 'López', 'Martínez', 'Sánchez', 'Torres', 'Hernández', 'Castro', 'Ramírez'];
BEGIN
    FOR i IN 1..10 LOOP
        INSERT INTO cliente (id, nombre, email, telefono, direccion)
        VALUES (
            nextval('id_cliente'),
            CONCAT(nombres[TRUNC(RANDOM() * array_length(nombres, 1) + 1)::INT], ' ', apellidos[TRUNC(RANDOM() * array_length(apellidos, 1) + 1)::INT]),
            CONCAT('cliente', i, '@example.com'),
            CONCAT('+57 300', TRUNC(1000000 + RANDOM() * 9000000)::TEXT),
            CONCAT('Calle ', i, ' #', i*10, '-', i)
        );
    END LOOP;
END $$;

call insertar_clientes();
select * from cliente;

CREATE OR REPLACE PROCEDURE insertar_asientos()
LANGUAGE plpgsql AS $$
DECLARE
    lugar_cursor CURSOR FOR 
        SELECT id, capacidad FROM lugar;
    id_lugar_v VARCHAR;
    capacidad_lugar_v INT;
    capacidad_actual INT;
    fila CHAR;
    columna INT;
    tipo_asiento TEXT;
    estado_asiento TEXT[] := ARRAY['disponible', 'reservado', 'vendido'];
    eventos RECORD;
    estado_seleccionado TEXT;
BEGIN
    OPEN lugar_cursor;
    LOOP
        FETCH lugar_cursor INTO id_lugar_v, capacidad_lugar_v;
        EXIT WHEN NOT FOUND;
        
        FOR eventos IN SELECT * FROM evento WHERE lugar_id = id_lugar_v LOOP
            fila := 'A'; 
            columna := 1; 
        
            FOR i IN 1..capacidad_lugar_v LOOP
                -- Determinar tipo de asiento según posición
                IF i <= capacidad_lugar_v * 0.1 THEN
                    tipo_asiento := 'palco'; -- 10% primeros asientos son "palco"
                ELSIF i <= capacidad_lugar_v * 0.3 THEN
                    tipo_asiento := 'vip'; -- 20% siguientes asientos son "vip"
                ELSE
                    tipo_asiento := 'general'; -- El resto son "general"
                END IF;

                INSERT INTO asiento (id, codigo, fila, columna, precio, descuento, tipo, estado, id_lugar)
                VALUES (
                    nextval('id_asiento'), 
                    nextval('codigo_asiento'), 
                    fila, 
                    columna, 
                    (CASE tipo_asiento
                        WHEN 'palco' THEN 50000
                        WHEN 'vip' THEN 30000
                        ELSE 20000
                     END), -- Precio según tipo
                    (CASE tipo_asiento
                        WHEN 'palco' THEN 0.1
                        WHEN 'vip' THEN 0.05
                        ELSE 0
                     END), -- Descuento según tipo
                    tipo_asiento, 
                    estado_asiento[TRUNC(RANDOM() * 3 + 1)::INT], 
                    id_lugar_v
                );

                -- Seleccionar el estado del asiento aleatoriamente
                estado_seleccionado := estado_asiento[TRUNC(RANDOM() * 3 + 1)::INT];

                INSERT INTO ocupacion_asientos (id_asiento, id_evento, estado)
                VALUES (
                    currval('id_asiento'), 
                    eventos.id,  
                    estado_seleccionado  
                );

                columna := columna + 1;
                IF columna > 10 THEN 
                    columna := 1;
                    fila := CHR(ASCII(fila) + 1); 
                END IF;
            END LOOP;
        END LOOP;
    END LOOP;
    CLOSE lugar_cursor;
END $$;


call insertar_asientos();

CREATE OR REPLACE PROCEDURE insertar_inventario()
LANGUAGE plpgsql AS $$
DECLARE
    v_total_disponible INT;
    v_total_vendido INT;
    v_total_reservado INT;
    v_lugar VARCHAR;
    v_evento VARCHAR;
BEGIN
    FOR v_lugar IN SELECT id FROM lugar LOOP

        FOR v_evento IN 
            SELECT id FROM evento WHERE lugar_id = v_lugar LIMIT 2 LOOP

            SELECT total_disponible, total_vendido, total_reservado
            INTO v_total_disponible, v_total_vendido, v_total_reservado
            FROM obtener_asientos(v_lugar, v_evento);

            INSERT INTO inventario (id, cantidad_disponible, cantidad_vendidos, cantidad_reservados, id_lugar, id_evento) 
            VALUES (nextval('codigo_inventario'), v_total_disponible, v_total_vendido, v_total_reservado, v_lugar, v_evento);
        END LOOP;
    END LOOP;
END;
$$;

---- fin procedimientos para poblar las entidades--------------------------

CREATE OR REPLACE FUNCTION obtener_asientos(p_id_lugar VARCHAR, p_id_evento VARCHAR)
RETURNS TABLE (
    total_disponible INT,
    total_vendido INT,
    total_reservado INT
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        CAST(COUNT(*) FILTER (WHERE o.estado = 'disponible') AS INT) AS total_disponible,
        CAST(COUNT(*) FILTER (WHERE o.estado = 'vendido') AS INT) AS total_vendido,
        CAST(COUNT(*) FILTER (WHERE o.estado = 'reservado') AS INT) AS total_reservado
    FROM ocupacion_asientos o
    JOIN asiento a ON a.id = o.id_asiento
    JOIN evento e ON e.id = o.id_evento
    WHERE a.id_lugar = p_id_lugar AND e.id = p_id_evento;
END;
$$ LANGUAGE plpgsql;

select * from obtener_asientos('1', '1');
select * from obtener_asientos('1', '2');


---- funciones con return query-----------------------------------------------

