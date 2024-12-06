package org.main;

import java.sql.Date;
import java.util.Scanner;

import org.models.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Cliente cliente = new Cliente();
        Artista artista = new Artista();
        Evento evento = new Evento();
        Lugar lugar = new Lugar();
        Asiento asiento = new Asiento();

        boolean continuar = true;

        // Bucle principal que permite volver al menú después de cada operación
        while (continuar) {
            // Mostrar el menú principal de "Conciertos YA"
            System.out.println("Bienvenido a Conciertos YA!");
            System.out.println("Seleccione una opción:");
            System.out.println("1. Cliente");
            System.out.println("2. Artistas");
            System.out.println("3. Eventos");
            System.out.println("4. Lugares");
            System.out.println("5. Asientos");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            int opcionPrincipal = scanner.nextInt();
            scanner.nextLine();

            switch (opcionPrincipal) {
                case 1:  // Cliente
                    // Menú Cliente
                    boolean continuarCliente = true;
                    while (continuarCliente) {
                        System.out.println("\nMenú Cliente:");
                        System.out.println("1. Consultar Cliente");
                        System.out.println("2. Crear Cliente");
                        System.out.println("3. Actualizar Cliente");
                        System.out.println("4. Eliminar Cliente");
                        System.out.println("5. Volver al Menú Principal");
                        System.out.print("Seleccione una opción: ");

                        int opcionCliente = scanner.nextInt();
                        scanner.nextLine();  // Consumir la nueva línea después de leer el número

                        switch (opcionCliente) {
                            case 1:  // Consultar Cliente
                                System.out.print("Ingresa el ID del cliente a consultar: ");
                                String clienteIdConsultar = scanner.nextLine();
                                cliente.leerCliente(clienteIdConsultar);
                                break;

                            case 2:  // Crear Cliente
                                System.out.print("Ingresa el ID del cliente: ");
                                String clienteId = scanner.nextLine();
                                System.out.print("Ingresa el nombre del cliente: ");
                                String clienteNombre = scanner.nextLine();
                                System.out.print("Ingresa el email del cliente: ");
                                String clienteEmail = scanner.nextLine();
                                System.out.print("Ingresa el teléfono del cliente: ");
                                String clienteTelefono = scanner.nextLine();
                                System.out.print("Ingresa la dirección del cliente: ");
                                String clienteDireccion = scanner.nextLine();
                                cliente.crearCliente(clienteId, clienteNombre, clienteEmail, clienteTelefono, clienteDireccion);
                                break;

                            case 3:  // Actualizar Cliente
                                System.out.print("Ingresa el ID del cliente a actualizar: ");
                                String clienteIdActualizar = scanner.nextLine();
                                System.out.print("Ingresa el nombre del cliente nuevo: ");
                                clienteNombre = scanner.nextLine();
                                System.out.print("Ingresa el email del cliente nuevo: ");
                                clienteEmail = scanner.nextLine();
                                System.out.print("Ingresa el teléfono del cliente nuevo: ");
                                clienteTelefono = scanner.nextLine();
                                System.out.print("Ingresa la dirección del cliente nueva: ");
                                clienteDireccion = scanner.nextLine();
                                cliente.modificarCliente(clienteIdActualizar, clienteNombre, clienteEmail, clienteTelefono, clienteDireccion);
                                break;

                            case 4:  // Eliminar Cliente
                                System.out.print("Ingresa el ID del cliente a eliminar: ");
                                String clienteIdEliminar = scanner.nextLine();
                                cliente.eliminarCliente(clienteIdEliminar);
                                break;

                            case 5:  // Volver al Menú Principal
                                continuarCliente = false;
                                break;

                            default:
                                System.out.println("Opción no válida para Cliente.");
                                break;
                        }
                    }
                    break;

                case 2:  // Artistas
                    // Menú Artistas
                    boolean continuarArtistas = true;
                    while (continuarArtistas) {
                        System.out.println("\nMenú Artistas:");
                        System.out.println("1. Consultar Artista");
                        System.out.println("2. Crear Artista");
                        System.out.println("3. Actualizar Artista");
                        System.out.println("4. Eliminar Artista");
                        System.out.println("5. Volver al Menú Principal");
                        System.out.print("Seleccione una opción: ");

                        int opcionArtista = scanner.nextInt();
                        scanner.nextLine();

                        switch (opcionArtista) {
                            case 1:  // Consultar Artista
                                System.out.print("Ingresa el ID del artista a consultar: ");
                                String artistaIdConsultar = scanner.nextLine();
                                artista.leerArtista(artistaIdConsultar);
                                break;

                            case 2:  // Crear Artista
                                System.out.print("Ingresa el ID del artista: ");
                                String ArtistaId = scanner.nextLine();
                                System.out.print("Ingresa el nombre del artista: ");
                                String ArtistaNombre = scanner.nextLine();
                                System.out.print("Escribe el género musical del artista (Salsa, Rock, Pop o Reguetton): ");
                                String ArtistaGeneroMusical = scanner.nextLine().toLowerCase();
                                artista.crearArtista(ArtistaId, ArtistaNombre, ArtistaGeneroMusical);
                                break;

                            case 3:  // Actualizar Artista
                                System.out.print("Ingresa el ID del artista a actualizar: ");
                                String artistaIdActualizar = scanner.nextLine();
                                System.out.print("Ingresa el nombre del artista nuevo: ");
                                String nuevoNombreArtista = scanner.nextLine();
                                System.out.print("Ingresa el nuevo género musical del artista (Salsa, Rock, Pop o Reguetton): ");
                                String nuevoEmailArtista = scanner.nextLine().toLowerCase();
                                artista.modificarArtista(artistaIdActualizar, nuevoNombreArtista, nuevoEmailArtista);
                                break;

                            case 4:  // Eliminar Artista
                                System.out.print("Ingresa el ID del artista a eliminar: ");
                                String artistaIdEliminar = scanner.nextLine();
                                artista.eliminarArtista(artistaIdEliminar);
                                break;

                            case 5:  // Volver al Menú Principal
                                continuarArtistas = false;
                                break;

                            default:
                                System.out.println("Opción no válida para Artistas.");
                                break;
                        }
                    }
                    break;

                case 3: // Menú Eventos
                    boolean continuarEventos = true;
                    while (continuarEventos) {
                        System.out.println("\nMenú Eventos:");
                        System.out.println("1. Consultar Evento");
                        System.out.println("2. Crear Evento");
                        System.out.println("3. Actualizar Evento");
                        System.out.println("4. Eliminar Evento");
                        System.out.println("5. Volver al Menú Principal");
                        System.out.print("Seleccione una opción: ");

                        int opcionEvento = scanner.nextInt();
                        scanner.nextLine();

                        switch (opcionEvento) {
                            case 1: // Consultar Evento
                                boolean continuarConsultarEvento = true;
                                while (continuarConsultarEvento) {
                                    System.out.println("\nMenú Consultar Evento:");
                                    System.out.println("1. Consultar evento por fecha");
                                    System.out.println("2. Consultar evento por artista");
                                    System.out.println("3. Consultar evento por lugar");
                                    System.out.println("4. Volver al Menú Eventos");
                                    System.out.print("Seleccione una opción: ");

                                    int opcionConsultarEvento = scanner.nextInt();
                                    scanner.nextLine();

                                    switch (opcionConsultarEvento) {
                                        case 1: // Consultar evento por fecha
                                            System.out.print("Ingresa la fecha del evento a consultar (yyyy-MM-dd): ");
                                            Date fechaEvento = Date.valueOf(scanner.nextLine());
                                            evento.buscarEventosPorFecha(fechaEvento);
                                            break;

                                        case 2: // Consultar evento por artista
                                            System.out.print("Ingresa el ID del artista del evento a consultar: ");
                                            String artistaEvento = scanner.nextLine();
                                            evento.buscarEventosPorArtista(artistaEvento);
                                            break;

                                        case 3: // Consultar evento por lugar
                                            System.out.print("Ingresa el ID del lugar del evento a consultar: ");
                                            String lugarEvento = scanner.nextLine();
                                            evento.buscarEventosPorLugar(lugarEvento);
                                            break;

                                        case 4:  // Volver al Menú Eventos
                                            continuarConsultarEvento = false;
                                            break;

                                        default:
                                            System.out.println("Opción no válida para consultar evento.");
                                            break;
                                    }
                                }
                                break;

                            case 2: // Crear Evento
                                System.out.print("Ingresa el ID del evento: ");
                                String eventoId = scanner.nextLine();
                                System.out.print("Ingresa el nombre del evento: ");
                                String eventoNombre = scanner.nextLine();
                                System.out.print("Ingresa la fecha del evento (yyyy-MM-dd): ");
                                String eventoFecha = scanner.nextLine();
                                System.out.println("Ingresa la hora del evento (HH:mm:ss): ");
                                String eventoHora = scanner.nextLine();
                                System.out.print("Ingresa la descripción del evento: ");
                                String eventoDescripcion = scanner.nextLine();
                                System.out.print("Ingresa el género musical del evento (Salsa, Rock, Pop o Reguetton): ");
                                String eventoGeneroMusical = scanner.nextLine().toLowerCase();
                                System.out.print("Ingresa el estado del evento (programado,cancelado,finalizado): ");
                                String eventoEstado = scanner.nextLine().toLowerCase();
                                System.out.print("Ingresa el cartel del evento: ");
                                String eventoCartel = scanner.nextLine();
                                System.out.print("Ingresa el ID del lugar del evento: ");
                                String lugarEventoId = scanner.nextLine();
                                evento.crearEvento(eventoId, eventoNombre, eventoFecha, eventoHora, eventoDescripcion, eventoGeneroMusical, eventoEstado, eventoCartel, lugarEventoId);
                                break;

                            case 3: // Actualizar Evento
                                System.out.print("Ingresa el ID del evento a actualizar: ");
                                String eventoIdActualizar = scanner.nextLine();
                                System.out.print("Ingresa el nuevo nombre del evento: ");
                                String eventoNuevoNombre = scanner.nextLine();
                                System.out.println("Ingresa la nueva fecha del evento (yyyy-MM-dd): ");
                                String eventoNuevaFecha = scanner.nextLine();
                                System.out.println("Ingresa la nueva hora del evento (HH:mm:ss): ");
                                String eventoNuevaHora = scanner.nextLine();
                                System.out.print("Ingresa la nueva descripción del evento: ");
                                String eventoNuevaDescripcion = scanner.nextLine();
                                System.out.print("Ingresa el nuevo género musical del evento (Salsa, Rock, Pop o Reguetton): ");
                                String eventoNuevoGeneroMusical = scanner.nextLine().toLowerCase();
                                System.out.print("Ingresa el nuevo estado del evento (programado,cancelado,finalizado): ");
                                String eventoNuevoEstado = scanner.nextLine().toLowerCase();
                                System.out.print("Ingresa el nuevo cartel del evento: ");
                                String eventoNuevoCartel = scanner.nextLine();
                                System.out.print("Ingresa el nuevo ID del lugar del evento: ");
                                String eventoNuevoLugarId = scanner.nextLine();
                                evento.modificarEvento(eventoIdActualizar, eventoNuevoNombre, eventoNuevaFecha, eventoNuevaHora, eventoNuevaDescripcion, eventoNuevoGeneroMusical, eventoNuevoEstado, eventoNuevoCartel, eventoNuevoLugarId);
                                break;

                            case 4: // Eliminar Evento
                                System.out.print("Ingresa el ID del evento a eliminar: ");
                                String eventoIdEliminar = scanner.nextLine();
                                evento.eliminarEvento(eventoIdEliminar);
                                break;

                            case 5: // Volver al Menú Principal
                                continuarEventos = false;
                                break;

                            default:
                                System.out.println("Opción no válida para Eventos.");
                                break;
                        }
                    }
                    break;

                case 6:  // Salir
                    continuar = false;
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
        scanner.close();
    }
}
