package com.humani.humani;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ProductoApp {

    private final Scanner scanner = new Scanner(System.in);

    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    public ProductoApp(
            UsuarioRepository usuarioRepository,
            ProductoRepository productoRepository) {

        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
    }

    // =========================================================
    // MENÚ PRINCIPAL
    // =========================================================

    public void iniciar() {

        int opcion;

        do {

            System.out.println();
            System.out.println("=================================");
            System.out.println("          SISTEMA HUMANI");
            System.out.println("=================================");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Mostrar usuarios");
            System.out.println("3. Eliminar usuario");
            System.out.println("4. Ir a productos");
            System.out.println("5. Salir");
            System.out.println("=================================");
            System.out.print("Seleccione una opción: ");

            opcion = leerEntero();

            switch (opcion) {

                case 1:
                    registrarUsuario();
                    break;

                case 2:
                    mostrarUsuarios();
                    break;

                case 3:
                    eliminarUsuario();
                    break;

                case 4:
                    menuProductos();
                    break;

                case 5:
                    System.out.println();
                    System.out.println(
                            "Gracias por utilizar el sistema."
                    );
                    break;

                default:
                    System.out.println();
                    System.out.println(
                            "❌ Opción no válida."
                    );
            }

        } while (opcion != 5);
    }

    // =========================================================
    // REGISTRAR USUARIO
    // =========================================================

    private void registrarUsuario() {

        System.out.println();
        System.out.println("===== REGISTRAR USUARIO =====");

        // ---------- NOMBRE ----------

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        while (nombre.trim().isEmpty()) {

            System.out.println(
                    "❌ El nombre no puede estar vacío."
            );

            System.out.print("Nombre: ");
            nombre = scanner.nextLine();
        }

        // ---------- DOCUMENTO ----------

        System.out.print("Documento: ");
        String documento = scanner.nextLine();

        while (documento.trim().isEmpty()) {

            System.out.println(
                    "❌ El documento no puede estar vacío."
            );

            System.out.print("Documento: ");
            documento = scanner.nextLine();
        }

        // ---------- COMPROBAR DOCUMENTO REPETIDO ----------

        while (!usuarioRepository
                .findByDocumento(documento)
                .isEmpty()) {

            System.out.println();
            System.out.println(
                    "❌ Ya existe un usuario con ese documento."
            );

            System.out.print(
                    "Ingrese un documento diferente: "
            );

            documento = scanner.nextLine();

            while (documento.trim().isEmpty()) {

                System.out.println(
                        "❌ El documento no puede estar vacío."
                );

                System.out.print("Documento: ");
                documento = scanner.nextLine();
            }
        }

        // ---------- CORREO ----------

        System.out.print("Correo: ");
        String correo = scanner.nextLine();

        while (correo.trim().isEmpty()) {

            System.out.println(
                    "❌ El correo no puede estar vacío."
            );

            System.out.print("Correo: ");
            correo = scanner.nextLine();
        }

        // ---------- GUARDAR USUARIO ----------

        Usuario usuario = new Usuario(
                nombre,
                documento,
                correo
        );

        usuarioRepository.save(usuario);

        System.out.println();
        System.out.println(
                "✅ Usuario registrado correctamente."
        );
    }

    // =========================================================
    // MOSTRAR USUARIOS
    // =========================================================

    private void mostrarUsuarios() {

        System.out.println();
        System.out.println("===== USUARIOS REGISTRADOS =====");

        List<Usuario> usuarios =
                usuarioRepository.findAll();

        if (usuarios.isEmpty()) {

            System.out.println(
                    "No hay usuarios registrados."
            );

            return;
        }

        for (Usuario usuario : usuarios) {

            usuario.mostrarUsuario();
        }
    }

    // =========================================================
    // ELIMINAR USUARIO
    // =========================================================

    private void eliminarUsuario() {

        System.out.println();
        System.out.println("===== ELIMINAR USUARIO =====");

        if (usuarioRepository.count() == 0) {

            System.out.println(
                    "No hay usuarios registrados."
            );

            return;
        }

        System.out.print(
                "Ingrese el documento del usuario: "
        );

        String documento = scanner.nextLine();

        while (documento.trim().isEmpty()) {

            System.out.println(
                    "❌ El documento no puede estar vacío."
            );

            System.out.print(
                    "Ingrese el documento del usuario: "
            );

            documento = scanner.nextLine();
        }

        List<Usuario> usuarios =
                usuarioRepository.findByDocumento(documento);

        // ---------- NO EXISTE ----------

        if (usuarios.isEmpty()) {

            System.out.println();
            System.out.println(
                    "❌ Usuario no encontrado."
            );

            return;
        }

        // ---------- HAY VARIOS CON EL MISMO DOCUMENTO ----------

        if (usuarios.size() > 1) {

            System.out.println();
            System.out.println(
                    "⚠️ Hay varios usuarios con ese documento."
            );

            System.out.println();
            System.out.println(
                    "Usuarios encontrados:"
            );

            for (Usuario usuario : usuarios) {

                usuario.mostrarUsuario();
            }

            System.out.println();

            System.out.print(
                    "Ingrese el ID del usuario que desea eliminar: "
            );

            int id = leerEntero();

            Usuario usuarioSeleccionado = null;

            for (Usuario usuario : usuarios) {

                if (usuario.getId() != null
                        && usuario.getId().equals((long) id)) {

                    usuarioSeleccionado = usuario;
                    break;
                }
            }

            if (usuarioSeleccionado == null) {

                System.out.println();
                System.out.println(
                        "❌ No existe un usuario con ese ID."
                );

                return;
            }

            System.out.println();
            System.out.println(
                    "Usuario seleccionado:"
            );

            usuarioSeleccionado.mostrarUsuario();

            System.out.print(
                    "¿Está seguro de eliminar este usuario? (S/N): "
            );

            String confirmacion =
                    scanner.nextLine();

            if (confirmacion.equalsIgnoreCase("S")) {

                usuarioRepository.delete(
                        usuarioSeleccionado
                );

                System.out.println();
                System.out.println(
                        "✅ Usuario eliminado correctamente."
                );

            } else {

                System.out.println();
                System.out.println(
                        "❌ Eliminación cancelada."
                );
            }

            return;
        }

        // ---------- SOLO EXISTE UN USUARIO ----------

        Usuario usuario = usuarios.get(0);

        System.out.println();
        System.out.println(
                "Usuario encontrado:"
        );

        usuario.mostrarUsuario();

        System.out.print(
                "¿Está seguro de eliminar este usuario? (S/N): "
        );

        String confirmacion =
                scanner.nextLine();

        if (confirmacion.equalsIgnoreCase("S")) {

            usuarioRepository.delete(usuario);

            System.out.println();
            System.out.println(
                    "✅ Usuario eliminado correctamente."
            );

        } else {

            System.out.println();
            System.out.println(
                    "❌ Eliminación cancelada."
            );
        }
    }

    // =========================================================
    // MENÚ PRODUCTOS
    // =========================================================

    private void menuProductos() {

        int opcion;

        do {

            System.out.println();
            System.out.println("=================================");
            System.out.println("            PRODUCTOS");
            System.out.println("=================================");
            System.out.println("1. Registrar producto");
            System.out.println("2. Mostrar productos");
            System.out.println("3. Buscar producto");
            System.out.println("4. Modificar producto");
            System.out.println("5. Eliminar producto");
            System.out.println("6. Volver");
            System.out.println("=================================");
            System.out.print("Seleccione una opción: ");

            opcion = leerEntero();

            switch (opcion) {

                case 1:
                    registrarProducto();
                    break;

                case 2:
                    mostrarProductos();
                    break;

                case 3:
                    buscarProducto();
                    break;

                case 4:
                    modificarProducto();
                    break;

                case 5:
                    eliminarProducto();
                    break;

                case 6:
                    System.out.println();
                    System.out.println(
                            "Regresando al menú principal..."
                    );
                    break;

                default:
                    System.out.println();
                    System.out.println(
                            "❌ Opción no válida."
                    );
            }

        } while (opcion != 6);
    }

    // =========================================================
    // REGISTRAR PRODUCTO
    // =========================================================

    private void registrarProducto() {

        System.out.println();
        System.out.println("===== REGISTRAR PRODUCTO =====");

        // ---------- NOMBRE ----------

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        while (nombre.trim().isEmpty()) {

            System.out.println(
                    "❌ El nombre del producto no puede estar vacío."
            );

            System.out.print("Nombre: ");
            nombre = scanner.nextLine();
        }

        // ---------- PRODUCTO REPETIDO ----------

        while (
                productoRepository
                        .existsByNombreIgnoreCase(nombre)
        ) {

            System.out.println();
            System.out.println(
                    "❌ Ya existe un producto con ese nombre."
            );

            System.out.print(
                    "Ingrese un nombre diferente: "
            );

            nombre = scanner.nextLine();

            while (nombre.trim().isEmpty()) {

                System.out.println(
                        "❌ El nombre del producto "
                                + "no puede estar vacío."
                );

                System.out.print("Nombre: ");
                nombre = scanner.nextLine();
            }
        }

        // ---------- CANTIDAD ----------

        System.out.print("Cantidad: ");
        int cantidad = leerCantidad();

        // ---------- CATEGORÍA ----------

        System.out.print("Categoría: ");
        String categoria = scanner.nextLine();

        while (categoria.trim().isEmpty()) {

            System.out.println(
                    "❌ La categoría no puede estar vacía."
            );

            System.out.print("Categoría: ");
            categoria = scanner.nextLine();
        }

        // ---------- ALMACENAMIENTO ----------

        System.out.print("Almacenamiento: ");
        String almacenamiento = scanner.nextLine();

        while (almacenamiento.trim().isEmpty()) {

            System.out.println(
                    "❌ El almacenamiento no puede estar vacío."
            );

            System.out.print(
                    "Almacenamiento: "
            );

            almacenamiento = scanner.nextLine();
        }

        // ---------- PRECIO ----------

        System.out.print("Precio: ");
        double precio = leerPrecio();

        // ---------- CREAR PRODUCTO ----------

        Producto producto = new Producto(
                nombre,
                cantidad,
                categoria,
                almacenamiento,
                precio
        );

        productoRepository.save(producto);

        System.out.println();
        System.out.println(
                "✅ Producto registrado correctamente."
        );
    }

    // =========================================================
    // MOSTRAR PRODUCTOS
    // =========================================================

    private void mostrarProductos() {

        System.out.println();
        System.out.println("===== PRODUCTOS =====");

        List<Producto> productos =
                productoRepository.findAll();

        if (productos.isEmpty()) {

            System.out.println(
                    "No hay productos registrados."
            );

            return;
        }

        for (Producto producto : productos) {

            producto.mostrarProducto();
        }
    }

    // =========================================================
    // BUSCAR PRODUCTO
    // =========================================================

    private void buscarProducto() {

        System.out.println();
        System.out.println("===== BUSCAR PRODUCTO =====");

        System.out.print(
                "Ingrese el nombre del producto: "
        );

        String nombre = scanner.nextLine();

        while (nombre.trim().isEmpty()) {

            System.out.println(
                    "❌ Debe ingresar un nombre."
            );

            System.out.print(
                    "Ingrese el nombre del producto: "
            );

            nombre = scanner.nextLine();
        }

        Producto producto =
                productoRepository
                        .findByNombreIgnoreCase(nombre);

        if (producto != null) {

            System.out.println();
            System.out.println(
                    "Producto encontrado:"
            );

            producto.mostrarProducto();

        } else {

            System.out.println();
            System.out.println(
                    "❌ Producto no encontrado."
            );
        }
    }

    // =========================================================
    // MODIFICAR PRODUCTO
    // =========================================================

    private void modificarProducto() {

        System.out.println();
        System.out.println("===== MODIFICAR PRODUCTO =====");

        System.out.print(
                "Ingrese el nombre del producto: "
        );

        String nombre = scanner.nextLine();

        while (nombre.trim().isEmpty()) {

            System.out.println(
                    "❌ Debe ingresar un nombre."
            );

            System.out.print(
                    "Ingrese el nombre del producto: "
            );

            nombre = scanner.nextLine();
        }

        Producto producto =
                productoRepository
                        .findByNombreIgnoreCase(nombre);

        if (producto == null) {

            System.out.println();
            System.out.println(
                    "❌ Producto no encontrado."
            );

            return;
        }

        System.out.println();
        System.out.println(
                "Ingrese los nuevos datos:"
        );

        // ---------- NUEVO NOMBRE ----------

        System.out.print("Nuevo nombre: ");
        String nuevoNombre = scanner.nextLine();

        while (nuevoNombre.trim().isEmpty()) {

            System.out.println(
                    "❌ El nombre no puede estar vacío."
            );

            System.out.print(
                    "Nuevo nombre: "
            );

            nuevoNombre = scanner.nextLine();
        }

        Producto productoConMismoNombre =
                productoRepository
                        .findByNombreIgnoreCase(nuevoNombre);

        while (
                productoConMismoNombre != null
                        &&
                        !productoConMismoNombre
                                .getId()
                                .equals(producto.getId())
        ) {

            System.out.println();
            System.out.println(
                    "❌ Ya existe otro producto con ese nombre."
            );

            System.out.print(
                    "Ingrese un nombre diferente: "
            );

            nuevoNombre = scanner.nextLine();

            while (nuevoNombre.trim().isEmpty()) {

                System.out.println(
                        "❌ El nombre no puede estar vacío."
                );

                System.out.print(
                        "Nuevo nombre: "
                );

                nuevoNombre = scanner.nextLine();
            }

            productoConMismoNombre =
                    productoRepository
                            .findByNombreIgnoreCase(
                                    nuevoNombre
                            );
        }

        // ---------- NUEVA CANTIDAD ----------

        System.out.print(
                "Nueva cantidad: "
        );

        int nuevaCantidad = leerCantidad();

        // ---------- NUEVA CATEGORÍA ----------

        System.out.print(
                "Nueva categoría: "
        );

        String nuevaCategoria =
                scanner.nextLine();

        while (nuevaCategoria.trim().isEmpty()) {

            System.out.println(
                    "❌ La categoría no puede estar vacía."
            );

            System.out.print(
                    "Nueva categoría: "
            );

            nuevaCategoria =
                    scanner.nextLine();
        }

        // ---------- NUEVO ALMACENAMIENTO ----------

        System.out.print(
                "Nuevo almacenamiento: "
        );

        String nuevoAlmacenamiento =
                scanner.nextLine();

        while (nuevoAlmacenamiento.trim().isEmpty()) {

            System.out.println(
                    "❌ El almacenamiento "
                            + "no puede estar vacío."
            );

            System.out.print(
                    "Nuevo almacenamiento: "
            );

            nuevoAlmacenamiento =
                    scanner.nextLine();
        }

        // ---------- NUEVO PRECIO ----------

        System.out.print(
                "Nuevo precio: "
        );

        double nuevoPrecio = leerPrecio();

        // ---------- GUARDAR CAMBIOS ----------

        producto.setNombre(nuevoNombre);
        producto.setCantidad(nuevaCantidad);
        producto.setCategoria(nuevaCategoria);
        producto.setAlmacenamiento(nuevoAlmacenamiento);
        producto.setPrecio(nuevoPrecio);

        productoRepository.save(producto);

        System.out.println();
        System.out.println(
                "✅ Producto modificado correctamente."
        );
    }

    // =========================================================
    // ELIMINAR PRODUCTO
    // =========================================================

    private void eliminarProducto() {

        System.out.println();
        System.out.println("===== ELIMINAR PRODUCTO =====");

        System.out.print(
                "Ingrese el nombre del producto: "
        );

        String nombre = scanner.nextLine();

        while (nombre.trim().isEmpty()) {

            System.out.println(
                    "❌ Debe ingresar un nombre."
            );

            System.out.print(
                    "Ingrese el nombre del producto: "
            );

            nombre = scanner.nextLine();
        }

        Producto producto =
                productoRepository
                        .findByNombreIgnoreCase(nombre);

        if (producto != null) {

            System.out.println();
            producto.mostrarProducto();

            System.out.print(
                    "¿Está seguro de eliminar este producto? (S/N): "
            );

            String confirmacion =
                    scanner.nextLine();

            if (confirmacion.equalsIgnoreCase("S")) {

                productoRepository.delete(producto);

                System.out.println();
                System.out.println(
                        "✅ Producto eliminado correctamente."
                );

            } else {

                System.out.println();
                System.out.println(
                        "❌ Eliminación cancelada."
                );
            }

        } else {

            System.out.println();
            System.out.println(
                    "❌ Producto no encontrado."
            );
        }
    }

    // =========================================================
    // LEER ENTERO
    // =========================================================

    private int leerEntero() {

        while (true) {

            try {

                int numero =
                        Integer.parseInt(
                                scanner.nextLine()
                        );

                return numero;

            } catch (NumberFormatException e) {

                System.out.print(
                        "❌ Ingrese un número válido: "
                );
            }
        }
    }

    // =========================================================
    // LEER CANTIDAD
    // =========================================================

    private int leerCantidad() {

        while (true) {

            try {

                int cantidad =
                        Integer.parseInt(
                                scanner.nextLine()
                        );

                if (cantidad < 0) {

                    System.out.print(
                            "❌ La cantidad no puede "
                                    + "ser negativa. "
                                    + "Ingrese nuevamente: "
                    );

                    continue;
                }

                return cantidad;

            } catch (NumberFormatException e) {

                System.out.print(
                        "❌ Ingrese una cantidad válida: "
                );
            }
        }
    }

    // =========================================================
    // LEER PRECIO
    // =========================================================

    private double leerPrecio() {

        while (true) {

            try {

                double precio =
                        Double.parseDouble(
                                scanner.nextLine()
                                        .replace(",", ".")
                        );

                if (precio < 0) {

                    System.out.print(
                            "❌ El precio no puede "
                                    + "ser negativo. "
                                    + "Ingrese nuevamente: "
                    );

                    continue;
                }

                return precio;

            } catch (NumberFormatException e) {

                System.out.print(
                        "❌ Ingrese un precio válido: "
                );
            }
        }
    }
}