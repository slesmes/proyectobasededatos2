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

create sequence id_factura
	start with 1
	increment by 1;

create sequence id_ticket
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
	tipo_pago varchar check (tipo_pago in ('efectivo', 'efectivo y tarjeta de credito', 'efectivo y tarjeta de credito conciertosya', 'tarjeta de credito y tarjeta conciertosya'))
)

create table factura(
	id varchar primary key,
	detalle xml
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

CREATE OR REPLACE FUNCTION obtener_precio_asiento(asiento_id VARCHAR)
RETURNS TABLE (
    precio_base NUMERIC,
    descuento_aplicado NUMERIC,
    precio_final NUMERIC
) 
LANGUAGE plpgsql AS $$
BEGIN
    RETURN QUERY
    SELECT 
        precio AS precio_base, 
        (precio * descuento) AS descuento_aplicado, 
        (precio - (precio * descuento)) AS precio_final
    FROM asiento
    WHERE id = asiento_id;
END $$;

select * from obtener_precio_asiento('1')


---- funciones con return query-----------------------------------------------
CREATE TRIGGER crear_factura_trigger
AFTER INSERT ON ticket
FOR EACH ROW
EXECUTE FUNCTION crear_factura();

CREATE TRIGGER trigger_eliminar_eventos_detallados_a
BEFORE DELETE ON artista
FOR EACH ROW
EXECUTE FUNCTION eliminar_eventos_detallados_a();

CREATE TRIGGER trigger_eliminar_eventos_detallados_e
BEFORE DELETE ON evento
FOR EACH ROW
EXECUTE FUNCTION eliminar_eventos_detallados_e();

CREATE TRIGGER trigger_eliminar_eventos_por_lugar
BEFORE DELETE ON lugar
FOR EACH ROW
EXECUTE FUNCTION eliminar_eventos_por_lugar();

CREATE TRIGGER trigger_eliminar_ocupacion_asientos
BEFORE DELETE ON evento
FOR EACH ROW
EXECUTE FUNCTION eliminar_ocupacion_asientos();


CREATE OR REPLACE FUNCTION crear_factura()
RETURNS TRIGGER
LANGUAGE plpgsql AS $$
DECLARE
    nombre_cliente TEXT;
BEGIN

    SELECT nombre INTO nombre_cliente
    FROM cliente
    WHERE id = NEW.id_cliente;


    INSERT INTO factura (id, detalle)
    VALUES (
        nextval('id_factura'), 
        XMLPARSE(
            DOCUMENT '<detalle>' ||
                '<cliente>' || nombre_cliente || '</cliente>' ||
                '<fecha_emision>' || CURRENT_DATE || '</fecha_emision>' ||
                '<total>0</total>' ||
                '<metodo_pago>efectivo</metodo_pago>' ||
            '</detalle>'
        )
    );

    RETURN NEW;
END $$;

CREATE OR REPLACE FUNCTION eliminar_eventos_detallados_a()
RETURNS TRIGGER AS $$
DECLARE
    eventos_count INTEGER; 
BEGIN
    SELECT COUNT(*) INTO eventos_count 
    FROM evento_detallado
    WHERE id_artista = OLD.id;

    IF eventos_count = 0 THEN
        RAISE NOTICE 'No se eliminaron eventos detallados porque no hay eventos asociados al artista ID %.', OLD.id;
    ELSE
        BEGIN
            DELETE FROM evento_detallado WHERE id_artista = OLD.id;
            RAISE NOTICE 'Se eliminaron % eventos detallados asociados al artista ID %.', eventos_count, OLD.id;
        EXCEPTION
            WHEN foreign_key_violation THEN
                RAISE NOTICE 'No se pudo eliminar eventos detallados por una violación de clave foránea para el artista ID %.', OLD.id;
            WHEN OTHERS THEN
                RAISE NOTICE 'Error desconocido al eliminar eventos detallados para el artista ID %: %', OLD.id, SQLERRM;
        END;
    END IF;

    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION eliminar_eventos_detallados_e()
RETURNS TRIGGER AS $$
DECLARE
    eventos_count INTEGER; 
BEGIN
    
    SELECT COUNT(*) INTO eventos_count 
    FROM evento_detallado 
    WHERE id_evento = OLD.id;

    IF eventos_count = 0 THEN
        RAISE NOTICE 'No se eliminaron eventos detallados porque no hay eventos asociados al evento ID %.', OLD.id;
    ELSE
        BEGIN
            DELETE FROM evento_detallado WHERE id_evento = OLD.id;
            RAISE NOTICE 'Se eliminaron % eventos detallados asociados al evento ID %.', eventos_count, OLD.id;
        EXCEPTION
            WHEN foreign_key_violation THEN
                RAISE NOTICE 'No se pudo eliminar eventos detallados por una violación de clave foránea para el evento ID %.', OLD.id;
            WHEN OTHERS THEN
                RAISE NOTICE 'Error desconocido al eliminar eventos detallados para el evento ID %: %', OLD.id, SQLERRM;
        END;
    END IF;

    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION eliminar_eventos_por_lugar()
RETURNS TRIGGER AS $$
DECLARE
    eventos_count INTEGER; 
BEGIN
    SELECT COUNT(*) INTO eventos_count 
    FROM evento 
    WHERE lugar_id = OLD.id;

    IF eventos_count = 0 THEN
        RAISE NOTICE 'No se eliminaron eventos porque no hay eventos asociados al lugar ID %.', OLD.id;
    ELSE
        BEGIN
            DELETE FROM evento WHERE lugar_id = OLD.id;
            RAISE NOTICE 'Se eliminaron % eventos asociados al lugar ID %.', eventos_count, OLD.id;
        EXCEPTION
            WHEN foreign_key_violation THEN
                RAISE NOTICE 'No se pudo eliminar eventos debido a una violación de clave foránea para el lugar ID %.', OLD.id;
            WHEN OTHERS THEN
                RAISE NOTICE 'Error desconocido al eliminar eventos para el lugar ID %: %', OLD.id, SQLERRM;
        END;
    END IF;

    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION eliminar_ocupacion_asientos()
RETURNS TRIGGER AS $$
BEGIN
    DELETE FROM ocupacion_asientos
    WHERE id_evento = OLD.id;

    RETURN OLD;  
END;
$$ LANGUAGE plpgsql;

------------triggers------------------------------------------------------------

CREATE OR REPLACE PROCEDURE crear_ticket(
    p_id_cliente VARCHAR,
    p_id_asiento VARCHAR,
    p_id_evento VARCHAR,
    p_metodo_pago VARCHAR
)
LANGUAGE plpgsql AS $$
DECLARE
    precio_base_v NUMERIC;
    descuento_aplicado_v NUMERIC;
    precio_final_v NUMERIC;
    estado_asiento VARCHAR;
    factura_id VARCHAR;
BEGIN
    SELECT estado INTO estado_asiento
    FROM asiento
    WHERE id = p_id_asiento;

    IF estado_asiento = 'vendido' OR estado_asiento = 'reservado' THEN
        RAISE EXCEPTION 'El asiento con ID % no está disponible (estado: %)', p_id_asiento, estado_asiento;
    END IF;

    SELECT precio_base, descuento_aplicado, precio_final
    INTO precio_base_v, descuento_aplicado_v, precio_final_v
    FROM obtener_precio_asiento(p_id_asiento);

    INSERT INTO ticket (id, fecha_compra, descuento, precio, precio_descuento, id_asiento, id_cliente, id_evento)
    VALUES (
        nextval('id_ticket'),
        CURRENT_DATE,
        descuento_aplicado_v,
        precio_base_v,
        precio_final_v,
        p_id_asiento,
        p_id_cliente,
        p_id_evento
    );


    UPDATE asiento
    SET estado = 'vendido'
    WHERE id = p_id_asiento;

	 CALL actualizar_metodo_pago_factura(p_id_cliente, CURRENT_DATE, p_metodo_pago);

END $$;

CALL crear_ticket('1', '1', '1', '30');

-------------------funciones del ticket----------------------

CREATE OR REPLACE PROCEDURE actualizar_metodo_pago_factura(
    p_id_cliente VARCHAR,
    p_fecha DATE,
    p_metodo_pago VARCHAR
)
LANGUAGE plpgsql AS $$
DECLARE
    factura_id VARCHAR;
	v_metodo_pago varchar;
BEGIN

    SELECT id INTO factura_id
    FROM factura
    WHERE detalle::text LIKE '%' || p_id_cliente || '%'
      AND detalle::text LIKE '%' || p_fecha || '%'
    ORDER BY id DESC
    LIMIT 1;

    IF factura_id IS NOT NULL THEN
		select * into v_metodo_pago from obtener_tipo_pago(p_metodo_pago);
        UPDATE factura
        SET detalle = XMLPARSE(
            DOCUMENT REPLACE(
                detalle::text,
                '<metodo_pago>efectivo</metodo_pago>',
                '<metodo_pago>' || v_metodo_pago || '</metodo_pago>'
            )
        )
        WHERE id = factura_id;
    ELSE
        RAISE NOTICE 'No se encontró una factura para el cliente % en la fecha %', p_id_cliente, p_fecha;
    END IF;
END $$;

---------------------funciones de la factura------------------------------------------------

CREATE OR REPLACE FUNCTION obtener_tipo_pago(p_id_metodo_pago VARCHAR)
RETURNS VARCHAR
LANGUAGE plpgsql AS $$
DECLARE
    tipo_pago_v VARCHAR;
BEGIN
    SELECT tipo_pago INTO tipo_pago_v
    FROM metodo_pago
    WHERE id = p_id_metodo_pago;

    IF NOT FOUND THEN
        RAISE EXCEPTION 'El ID del método de pago % no es válido. Debe ser uno de los valores existentes (1, 2, 3, 4)', p_id_metodo_pago;
    END IF;

    RETURN tipo_pago_v;
END $$;

---------------funciones del metodo de pago--------------------------------------

---- CRUD ARTISTA -----

CREATE OR REPLACE PROCEDURE crear_artista(
    p_id VARCHAR,
    p_nombre VARCHAR,
    p_genero_musical VARCHAR
)
LANGUAGE plpgsql AS $$
BEGIN
    INSERT INTO artista (id, nombre, genero_musical)
    VALUES (p_id, p_nombre, p_genero_musical);
EXCEPTION
    WHEN unique_violation THEN
        RAISE NOTICE 'Error: Ya existe un artista con el ID %', p_id;
    WHEN check_violation THEN
        RAISE NOTICE 'Error: El género musical % no es válido.', p_genero_musical;
    WHEN OTHERS THEN
        RAISE NOTICE 'Error desconocido: %', SQLERRM;
END;
$$;

CREATE OR REPLACE PROCEDURE modificar_artista(
    p_id VARCHAR,
    p_nombre VARCHAR,
    p_genero_musical VARCHAR
)
LANGUAGE plpgsql AS $$
BEGIN
    UPDATE artista
    SET nombre = p_nombre, genero_musical = p_genero_musical
    WHERE id = p_id;

    IF NOT FOUND THEN
        RAISE NOTICE 'No se encontró ningún artista con ID % para modificar.', p_id;
    END IF;
EXCEPTION
    WHEN check_violation THEN
        RAISE NOTICE 'Error: El género musical % no es válido.', p_genero_musical;
    WHEN OTHERS THEN
        RAISE NOTICE 'Error desconocido al modificar el artista: %', SQLERRM;
END;
$$;


CREATE OR REPLACE PROCEDURE eliminar_artista(
    p_id VARCHAR
)
LANGUAGE plpgsql AS $$
BEGIN

    DELETE FROM artista WHERE id = p_id;

    IF NOT FOUND THEN
        RAISE NOTICE 'No se encontró ningún artista con ID %', p_id;
    END IF;
EXCEPTION
    WHEN foreign_key_violation THEN
        RAISE NOTICE 'Error: No se puede eliminar el artista % debido a restricciones de clave foránea.', p_id;
    WHEN OTHERS THEN
        RAISE NOTICE 'Error desconocido al eliminar el artista: %', SQLERRM;
END;
$$;

-- FINAL CRUD ARTISTA

-- CRUD EVENTO DETALLADO

CREATE OR REPLACE PROCEDURE crear_evento_detallado(
    p_id VARCHAR,
    p_id_evento VARCHAR,
    p_id_artista VARCHAR
)
LANGUAGE plpgsql AS $$
BEGIN
    INSERT INTO evento_detallado (id, id_evento, id_artista)
    VALUES (p_id, p_id_evento, p_id_artista);
EXCEPTION
    WHEN foreign_key_violation THEN
        RAISE NOTICE 'Error: El ID de evento % o el ID de artista % no existen.', p_id_evento, p_id_artista;
    WHEN unique_violation THEN
        RAISE NOTICE 'Error: Ya existe un evento detallado con el ID %', p_id;
    WHEN OTHERS THEN
        RAISE NOTICE 'Error desconocido: %', SQLERRM;
END;
$$;

CREATE OR REPLACE PROCEDURE modificar_evento_detallado(
    p_id VARCHAR,
    p_id_evento VARCHAR,
    p_id_artista VARCHAR
)
LANGUAGE plpgsql AS $$
BEGIN
    UPDATE evento_detallado
    SET id_evento = p_id_evento, id_artista = p_id_artista
    WHERE id = p_id;

    IF NOT FOUND THEN
        RAISE NOTICE 'No se encontró ningún evento detallado con ID % para modificar.', p_id;
    END IF;
EXCEPTION
    WHEN foreign_key_violation THEN
        RAISE NOTICE 'Error: Los IDs de evento (%), artista (%), o ambos no existen.', p_id_evento, p_id_artista;
    WHEN OTHERS THEN
        RAISE NOTICE 'Error desconocido al modificar evento detallado: %', SQLERRM;
END;
$$;

CREATE OR REPLACE PROCEDURE eliminar_evento_detallado(
    p_id VARCHAR
)
LANGUAGE plpgsql AS $$
BEGIN
    DELETE FROM evento_detallado WHERE id = p_id;

    IF NOT FOUND THEN
        RAISE NOTICE 'No se encontró ningún evento detallado con ID % para eliminar.', p_id;
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        RAISE NOTICE 'Error desconocido al eliminar evento detallado: %', SQLERRM;
END;
$$;

-- FINAL CRUD EVENTO DETALLADO

-- CRUD EVENTO 

CREATE OR REPLACE PROCEDURE crear_evento(
    p_id VARCHAR,
    p_nombre VARCHAR,
    p_fecha DATE,
    p_hora TIME,
    p_descripcion VARCHAR,
    p_genero_musical VARCHAR,
    p_estado VARCHAR,
    p_cartel VARCHAR,
    p_lugar_id VARCHAR
)
LANGUAGE plpgsql AS $$
BEGIN
    INSERT INTO evento (id, nombre, fecha, hora, descripcion, genero_musical, estado, cartel, lugar_id)
    VALUES (p_id, p_nombre, p_fecha, p_hora, p_descripcion, p_genero_musical, p_estado, p_cartel, p_lugar_id);
EXCEPTION
    WHEN unique_violation THEN
        RAISE NOTICE 'Error: Ya existe un evento con el ID %', p_id;
    WHEN check_violation THEN
        RAISE NOTICE 'Error: El valor % no es válido para el campo %', p_genero_musical, 'genero_musical';
    WHEN OTHERS THEN
        RAISE NOTICE 'Error desconocido al crear el evento: %', SQLERRM;
END;
$$;

CREATE OR REPLACE PROCEDURE modificar_evento(
    p_id VARCHAR,
    p_nombre VARCHAR,
    p_fecha DATE,
    p_hora TIME,
    p_descripcion VARCHAR,
    p_genero_musical VARCHAR,
    p_estado VARCHAR,
    p_cartel VARCHAR,
    p_lugar_id VARCHAR
)
LANGUAGE plpgsql AS $$
BEGIN
    UPDATE evento
    SET nombre = p_nombre, fecha = p_fecha, hora = p_hora, descripcion = p_descripcion, 
        genero_musical = p_genero_musical, estado = p_estado, cartel = p_cartel, lugar_id = p_lugar_id
    WHERE id = p_id;

    IF NOT FOUND THEN
        RAISE NOTICE 'No se encontró ningún evento con ID % para modificar.', p_id;
    END IF;
EXCEPTION
    WHEN check_violation THEN
        RAISE NOTICE 'Error: El valor % no es válido para el campo %', p_genero_musical, 'genero_musical';
    WHEN OTHERS THEN
        RAISE NOTICE 'Error desconocido al modificar el evento: %', SQLERRM;
END;
$$;

CREATE OR REPLACE PROCEDURE eliminar_evento(
    p_id VARCHAR
)
LANGUAGE plpgsql AS $$
BEGIN
    DELETE FROM evento WHERE id = p_id;

    IF NOT FOUND THEN
        RAISE NOTICE 'No se encontró ningún evento con ID % para eliminar.', p_id;
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        RAISE NOTICE 'Error desconocido al eliminar el evento: %', SQLERRM;
END;
$$;

--FINAL CRUD EVENTO


-- CRUD LUGAR

CREATE OR REPLACE PROCEDURE crear_lugar(
    p_id VARCHAR,
    p_nombre VARCHAR,
    p_direccion VARCHAR,
    p_capacidad INT,
    p_ciudad VARCHAR,
    p_imagen VARCHAR
)
LANGUAGE plpgsql AS $$
BEGIN
    INSERT INTO lugar (id, nombre, direccion, capacidad, ciudad, imagen)
    VALUES (p_id, p_nombre, p_direccion, p_capacidad, p_ciudad, p_imagen);
EXCEPTION
    WHEN unique_violation THEN
        RAISE NOTICE 'Error: Ya existe un lugar con el ID %', p_id;
    WHEN OTHERS THEN
        RAISE NOTICE 'Error desconocido al crear el lugar: %', SQLERRM;
END;
$$;

CREATE OR REPLACE PROCEDURE modificar_lugar(
    p_id VARCHAR,
    p_nombre VARCHAR,
    p_direccion VARCHAR,
    p_capacidad INT,
    p_ciudad VARCHAR,
    p_imagen VARCHAR
)
LANGUAGE plpgsql AS $$
BEGIN
    UPDATE lugar
    SET nombre = p_nombre, direccion = p_direccion, capacidad = p_capacidad, ciudad = p_ciudad, imagen = p_imagen
    WHERE id = p_id;

    IF NOT FOUND THEN
        RAISE NOTICE 'No se encontró ningún lugar con ID % para modificar.', p_id;
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        RAISE NOTICE 'Error desconocido al modificar el lugar: %', SQLERRM;
END;
$$;

CREATE OR REPLACE PROCEDURE eliminar_lugar(
    p_id VARCHAR
)
LANGUAGE plpgsql AS $$
BEGIN
    DELETE FROM lugar WHERE id = p_id;

    IF NOT FOUND THEN
        RAISE NOTICE 'No se encontró ningún lugar con ID % para eliminar.', p_id;
    END IF;
EXCEPTION
    WHEN foreign_key_violation THEN
        RAISE NOTICE 'Error: No se puede eliminar el lugar % debido a restricciones de clave foránea.', p_id;
    WHEN OTHERS THEN
        RAISE NOTICE 'Error desconocido al eliminar el lugar: %', SQLERRM;
END;
$$;

--FINAL CRUD LUGAR------------------------------

CREATE OR REPLACE PROCEDURE crear_asiento(
    p_id VARCHAR,
    p_codigo VARCHAR,
    p_fila VARCHAR,
    p_columna VARCHAR,
    p_precio NUMERIC,
    p_descuento NUMERIC,
    p_tipo VARCHAR,
    p_estado VARCHAR,
    p_id_lugar VARCHAR
)
LANGUAGE plpgsql AS $$
BEGIN
    INSERT INTO asiento (id, codigo, fila, columna, precio, descuento, tipo, estado, id_lugar)
    VALUES (p_id, p_codigo, p_fila, p_columna, p_precio, p_descuento, p_tipo, p_estado, p_id_lugar);
EXCEPTION
    WHEN unique_violation THEN
        RAISE NOTICE 'Error: Ya existe un asiento con el ID %', p_id;
    WHEN foreign_key_violation THEN
        RAISE NOTICE 'Error: El ID de lugar % no existe.', p_id_lugar;
    WHEN check_violation THEN
        RAISE NOTICE 'Error: El tipo % o el estado % no son válidos.', p_tipo, p_estado;
    WHEN OTHERS THEN
        RAISE NOTICE 'Error desconocido: %', SQLERRM;
END;
$$;

CREATE OR REPLACE PROCEDURE modificar_asiento(
    p_id VARCHAR,
    p_codigo VARCHAR,
    p_fila VARCHAR,
    p_columna VARCHAR,
    p_precio NUMERIC,
    p_descuento NUMERIC,
    p_tipo VARCHAR,
    p_estado VARCHAR,
    p_id_lugar VARCHAR
)
LANGUAGE plpgsql AS $$
BEGIN
    UPDATE asiento
    SET codigo = p_codigo,
        fila = p_fila,
        columna = p_columna,
        precio = p_precio,
        descuento = p_descuento,
        tipo = p_tipo,
        estado = p_estado,
        id_lugar = p_id_lugar
    WHERE id = p_id;

    IF NOT FOUND THEN
        RAISE NOTICE 'No se encontró ningún asiento con ID % para modificar.', p_id;
    END IF;
EXCEPTION
    WHEN foreign_key_violation THEN
        RAISE NOTICE 'Error: El ID de lugar % no existe.', p_id_lugar;
    WHEN check_violation THEN
        RAISE NOTICE 'Error: El tipo % o el estado % no son válidos.', p_tipo, p_estado;
    WHEN OTHERS THEN
        RAISE NOTICE 'Error desconocido al modificar el asiento: %', SQLERRM;
END;
$$;

CREATE OR REPLACE PROCEDURE eliminar_asiento(
    p_id VARCHAR
)
LANGUAGE plpgsql AS $$
BEGIN
    DELETE FROM asiento WHERE id = p_id;

    IF NOT FOUND THEN
        RAISE NOTICE 'No se encontró ningún asiento con ID %', p_id;
    END IF;
EXCEPTION
    WHEN foreign_key_violation THEN
        RAISE NOTICE 'No se puede eliminar el asiento % debido a restricciones de clave foránea.', p_id;
    WHEN OTHERS THEN
        RAISE NOTICE 'Error desconocido al eliminar el asiento: %', SQLERRM;
END;
$$;


-- Final CRUD ASIENTOS---------------------------

CREATE OR REPLACE PROCEDURE crear_cliente(
    p_id VARCHAR,
    p_nombre VARCHAR,
    p_email VARCHAR,
    p_telefono VARCHAR,
    p_direccion VARCHAR
)
LANGUAGE plpgsql AS $$
BEGIN
    INSERT INTO cliente (id, nombre, email, telefono, direccion)
    VALUES (p_id, p_nombre, p_email, p_telefono, p_direccion);
EXCEPTION
    WHEN unique_violation THEN
        RAISE NOTICE 'Error: Ya existe un cliente con el ID %', p_id;
    WHEN OTHERS THEN
        RAISE NOTICE 'Error desconocido: %', SQLERRM;
END;
$$;

CREATE OR REPLACE PROCEDURE leer_cliente(
    p_id VARCHAR
)
LANGUAGE plpgsql AS $$
BEGIN
    -- Consulta del cliente por su ID
    RAISE NOTICE 'Cliente: %', (SELECT row_to_json(cliente) FROM cliente WHERE id = p_id);
    -- O si prefieres devolverlo en un conjunto de resultados, puedes usar SELECT
    -- SELECT * FROM cliente WHERE id = p_id;
EXCEPTION
    WHEN no_data_found THEN
        RAISE NOTICE 'No se encontró un cliente con el ID %', p_id;
    WHEN OTHERS THEN
        RAISE NOTICE 'Error al consultar el cliente: %', SQLERRM;
END;
$$;

CREATE OR REPLACE PROCEDURE eliminar_cliente(
    p_id VARCHAR
)
LANGUAGE plpgsql AS $$
BEGIN
    DELETE FROM cliente WHERE id = p_id;

    IF NOT FOUND THEN
        RAISE NOTICE 'No se encontró ningún cliente con ID %', p_id;
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        RAISE NOTICE 'Error desconocido al eliminar el cliente: %', SQLERRM;
END;
$$;

-- CRUD CLIENTES------------------------------------

-- Trigger evento: FUNCION PARA ACTUALIZAR ESTADO DE EVENTOS AL ELIMINAR LUGAR

CREATE OR REPLACE FUNCTION cancelar_eventos_por_lugar()
RETURNS TRIGGER AS $$
DECLARE
    eventos_count INTEGER;
BEGIN
    -- Contar los eventos asociados al lugar
    SELECT COUNT(*) INTO eventos_count 
    FROM evento 
    WHERE lugar_id = OLD.id;

    IF eventos_count = 0 THEN
        RAISE NOTICE 'No se modificaron eventos porque no hay eventos asociados al lugar ID %.', OLD.id;
    ELSE
        BEGIN
            -- Actualizar el estado a "cancelado" y establecer lugar_id a NULL
            UPDATE evento 
            SET estado = 'cancelado', lugar_id = NULL
            WHERE lugar_id = OLD.id;

            RAISE NOTICE 'Se actualizaron % eventos asociados al lugar ID %.', eventos_count, OLD.id;
        EXCEPTION
            WHEN foreign_key_violation THEN
                RAISE NOTICE 'No se pudo actualizar eventos debido a una violación de clave foránea para el lugar ID %.', OLD.id;
            WHEN OTHERS THEN
                RAISE NOTICE 'Error desconocido al actualizar eventos para el lugar ID %: %', OLD.id, SQLERRM;
        END;
    END IF;

    RETURN OLD;
END;
$$ LANGUAGE plpgsql;


-- TRIGGER PARA EJECUTAR LA FUNCION ANTERIOR AL ELIMINAR UN LUGAR
CREATE TRIGGER trigger_cancelar_eventos_por_lugar
BEFORE DELETE ON lugar
FOR EACH ROW
EXECUTE FUNCTION cancelar_eventos_por_lugar();

-- Trigger ocupacion_asiento: cuando se elimina un evento tambien se elimina la ocupacion de asiento

CREATE OR REPLACE FUNCTION eliminar_ocupacion_asientos()
RETURNS TRIGGER AS $$
BEGIN
    -- Eliminar las ocupaciones de asiento asociadas al evento que se elimina
    DELETE FROM ocupacion_asientos
    WHERE id_evento = OLD.id;

    RETURN OLD;  
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_eliminar_ocupacion_asientos
BEFORE DELETE ON evento
FOR EACH ROW
EXECUTE FUNCTION eliminar_ocupacion_asientos();