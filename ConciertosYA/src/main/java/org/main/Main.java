package org.main;

import java.util.Scanner;
import org.models.Cliente;
import org.models.Artista;

public class Main {
    public static void main(String[] args) {
        // Creamos el objeto Scanner para leer la entrada del usuario
        Scanner scanner = new Scanner(System.in);

        // Instanciamos las clases necesarias
        Cliente cliente = new Cliente();
        Artista artista = new Artista();

        // Variable para controlar el bucle del menú
        boolean continuar = true;

        // Bucle principal que permite volver al menú después de cada operación
        while (continuar) {
            // Mostrar el menú principal de "Conciertos YA"
            System.out.println("Bienvenido a Conciertos YA!");
            System.out.println("Seleccione una opción:");
            System.out.println("1. Cliente");
            System.out.println("2. Artistas");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            int opcionPrincipal = scanner.nextInt();
            scanner.nextLine();  // Consumir la nueva línea después de leer el número

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
                                // Aquí puedes agregar el código para actualizar los datos de un cliente.
                                // Por ejemplo, podrías pedir los nuevos valores y llamar al método correspondiente.
                                System.out.print("Ingresa el nombre del cliente nuevo: ");
                                 clienteNombre = scanner.nextLine();
                                System.out.print("Ingresa el email del cliente nuevo: ");
                                 clienteEmail = scanner.nextLine();
                                System.out.print("Ingresa el teléfono del cliente nuevo: ");
                                 clienteTelefono = scanner.nextLine();
                                System.out.print("Ingresa la dirección del cliente nuevo: ");
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
                        scanner.nextLine();  // Consumir la nueva línea después de leer el número

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
                                System.out.print("Ingresa el email del artista: ");
                                String ArtistaGeneroMusical = scanner.nextLine();
                                artista.crearArtista(ArtistaId, ArtistaNombre, ArtistaGeneroMusical);
                                break;

                            case 3:  // Actualizar Artista
                                System.out.print("Ingresa el ID del artista a actualizar: ");
                                String artistaIdActualizar = scanner.nextLine();
                                System.out.print("Ingresa el nombre del artista nuevo: ");
                                String nuevoNombreArtista = scanner.nextLine();
                                System.out.print("Ingresa el email del artista nuevo: ");
                                String nuevoEmailArtista = scanner.nextLine();
                                System.out.print("Ingresa el teléfono del artista nuevo: ");
                                String nuevoTelefonoArtista = scanner.nextLine();
                                System.out.print("Ingresa la dirección del artista nuevo: ");
                                String nuevaDireccionArtista = scanner.nextLine();
                                //artista.modificarArtista(artistaIdActualizar, nuevoNombreArtista, nuevoEmailArtista, nuevoTelefonoArtista, nuevaDireccionArtista);
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

                case 3:  // Salir
                    System.out.println("Gracias por utilizar Conciertos YA. ¡Hasta luego!");
                    continuar = false;
                    break;

                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
                    break;
            
            }
        }

        // Cerrar el Scanner al finalizar la lectura
        scanner.close();
    }
}
