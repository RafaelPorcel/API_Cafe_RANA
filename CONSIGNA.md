Sistema de gestión para Café RANA
El café RANA abrirá sus puertas en Málaga y servirá dos productos principales:

Café
Muffins locos
Se necesita construir una API REST en Spring Boot que permita gestionar estos productos y las ventas realizadas.



:wrench: Requerimientos

Entidades principales
Producto (id, nombre, precio, stock).
Venta (id, fecha, lista de productos vendidos, total).

DTOs
ProductoDto → para devolver información sin mostrar campos internos como stock.
VentaDto → para devolver información de la venta con el detalle de productos y el total.
CrearVentaDto → para recibir la lista de productos que el cliente compra.

CRUD completo
Alta, baja, modificación y consulta de productos.
Alta y consulta de ventas.

Lógica de negocio
Si el cliente compra más de 3 muffins locos, se aplica un 10% de descuento en el total.
Si el cliente compra más de 5 cafés, se aplica un 2x1 en uno de ellos.
El stock debe disminuir al registrar una venta. Si no hay stock suficiente, debe lanzarse un error.

Capas
Controller → recibe las peticiones y devuelve DTOs.
Service → contiene la lógica de negocio (descuentos, validaciones).
Repository simulado → colección en memoria (Map y Map).

Manejo centralizado de errores
Usar @ControllerAdvice para capturar excepciones personalizadas como SinStockException o ProductoNoEncontradoException.
Devolver un JSON claro con el mensaje de error y el código HTTP correspondiente.

Uso de @Autowired

Inyectar el Repository en el Service.
Inyectar el Service en el Controller.


:test_tube:Flujo esperado

POST /api/productos → Crear un producto (ej: café, muffin loco).
GET /api/productos → Listar productos disponibles.
POST /api/ventas → Registrar una venta con lista de productos.
Si hay stock suficiente, se descuenta y se calcula el total con las reglas de negocio.
Si no hay stock, se devuelve un error manejado globalmente.

GET /api/ventas/{id} → Consultar una venta específica.