package co.edu.uniquindio.poo.viewController;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import co.edu.uniquindio.poo.App;
import co.edu.uniquindio.poo.controller.EmpresaController;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Empleado;
import co.edu.uniquindio.poo.model.Reserva;
import co.edu.uniquindio.poo.model.Vehiculo;

public class AdminViewController {

    private App app;
    private EmpresaController empresaController;
    private ObservableList<Cliente> listClientes = FXCollections.observableArrayList();
    private ObservableList<Empleado> listEmpleados = FXCollections.observableArrayList();
    private ObservableList<Reserva> reservas = FXCollections.observableArrayList();
    private Cliente selectedCliente;
    private Empleado selectedEmpleado;

    /**
     * Campos para Reservas
     */
    @FXML
    private Label lbl_FechaInicio, lbl_FechaFin;
    @FXML
    private ChoiceBox<Cliente> cb_Cliente;
    @FXML
    private ChoiceBox<Vehiculo> cb_Modelo;
    @FXML
    private DatePicker datePickerInicio, datePickerFin;
    @FXML
    private TableView<Reserva> tbl_ListReservas;
    @FXML
    private TableColumn<Reserva, String> tbc_Fecha, tbc_Cedula, tbc_Nombre, tbc_Telefono, tbc_TipoVehiculo, tbc_Marca,
            tbc_Modelo, tbc_Matricula;
    @FXML
    private TableColumn<Reserva, Double> tbc_Valor;
    @FXML
    private Button btn_Crear, btn_Eliminar, btn_Actualizar, btn_LimpiarReservas;
    @FXML
    private ImageView imageView;

    /**
     * Campos para Clientes
     */
    @FXML
    private TextField txt_Cedula, txt_Nombre, txt_Correo, txt_Telefono, txt_Direccion, txt_Clave, txt_Apellido;
    @FXML
    private TableView<Cliente> tbl_ListClientes;
    @FXML
    private TableColumn<Cliente, String> tbc_CedulaCliente, tbc_NombreCliente, tbc_ApellidoCliente, tbc_TelefonoCliente,
            tbc_CorreoCliente, tbc_DireccionCliente, tbc_ClaveCliente;
    @FXML
    private Button btn_CrearCliente, btn_EliminarCliente, btn_ActualizarCliente, btn_LimpiarClientes, btn_Atras;

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    /**
     * Campos para Empleados
     */
    @FXML
    private TextField txt_CedulaEmpleado, txt_NombreEmpleado, txt_CorreoEmpleado, txt_TelefonoEmpleado,
            txt_DireccionEmpleado, txt_ClaveEmpleado, txt_ApellidoEmpleado, txt_SalarioEmpleado;
    @FXML
    private TableView<Empleado> tbl_ListEmpleados;
    @FXML
    private TableColumn<Empleado, String> tbc_CedulaEmpleado, tbc_NombreEmpleado, tbc_ApellidoEmpleado,
            tbc_TelefonoEmpleado, tbc_CorreoEmpleado, tbc_DireccionEmpleado, tbc_ClaveEmpleado, tbc_SalarioEmpleado;
    @FXML
    private Button btn_CrearEmpleado, btn_EliminarEmpleado, btn_ActualizarEmpleados, btn_LimpiarEmpleados;

    @FXML
    void initialize() {
        empresaController = new EmpresaController(app.empresa);

        // Inicializar tablas y ChoiceBox
        initReservaView();
        initClienteView();
        initEmpleadoView();

        // Cargar datos de clientes y llenar ChoiceBox
        obtenerClientes();
        obtenerEmpleados();
        cb_Cliente.setItems(listClientes);
    }

    /**
     * Métodos para gestionar la vista de reservas
     */

    private void initView() {
        /**
         * Traer los datos del cliente a la tabla
         */
        initDataBinding();

        /**
         * Obtiene la lista
         */
        obtenerClientes();
        obtenerEmpleados();

        /**
         * Limpiar la tabla
         */
        tbl_ListClientes.getItems().clear();
        tbl_ListEmpleados.getItems().clear();

        /*
         * Agregar los elementos a la tabla
         */
        tbl_ListClientes.setItems(listClientes);
        tbl_ListEmpleados.setItems(listEmpleados);

        /**
         * Seleccionar elemento de la tabla
         */
        listenerSelectionCliente();
        listenerSelectionEmpleado();
    }

    private void initDataBinding() {
        tbc_CedulaCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCedula()));
        tbc_NombreCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tbc_ApellidoCliente
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
        tbc_CorreoCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCorreo()));
        tbc_DireccionCliente
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion()));
        tbc_TelefonoCliente
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
        tbc_ClaveCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClave()));
        tbc_CedulaEmpleado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCedula()));
        tbc_NombreEmpleado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tbc_ApellidoEmpleado
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
        tbc_CorreoEmpleado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCorreo()));
        tbc_DireccionEmpleado
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion()));
        tbc_TelefonoEmpleado
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
        tbc_ClaveEmpleado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClave()));
        tbc_SalarioEmpleado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSalario()));

    }

    private void initReservaView() {
        tbc_Fecha.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        tbc_Cedula.setCellValueFactory(new PropertyValueFactory<>("cedulaCliente"));
        tbc_Nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tbc_Telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        tbc_TipoVehiculo.setCellValueFactory(new PropertyValueFactory<>("tipoVehiculo"));
        tbc_Marca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        tbc_Modelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        tbc_Matricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        tbc_Valor.setCellValueFactory(new PropertyValueFactory<>("valorReserva"));

        tbl_ListReservas.setItems(reservas);
    }

    @FXML
    private void Limpiar(ActionEvent event) {
        limpiarReservas();
    }

    @FXML
    private void limpiarReservas() {
        reservas.clear(); // Limpia la lista de reservas
        tbl_ListReservas.refresh(); // Refresca la tabla para mostrar los cambios
    }

    @FXML
    private void limpiarClientes() {
        listClientes.clear(); // Limpia la lista de clientes
        tbl_ListClientes.refresh(); // Refresca la tabla para mostrar los cambios
    }

    @FXML
    private void limpiarEmpleados() {
        listEmpleados.clear(); // Limpia la lista de clientes
        tbl_ListEmpleados.refresh(); // Refresca la tabla para mostrar los cambios
    }

    @FXML
    private void crearReserva() {
        LocalDate fechaInicio = datePickerInicio.getValue();
        LocalDate fechaFin = datePickerFin.getValue();
        Cliente cliente = cb_Cliente.getValue();
        Vehiculo vehiculo = cb_Modelo.getValue();

        if (fechaInicio != null && fechaFin != null && cliente != null && vehiculo != null) {
            double valorReserva = calcularValorReserva(fechaInicio, fechaFin);
            Reserva reserva = new Reserva(fechaInicio, fechaFin, cliente, vehiculo);
            reservas.add(reserva);
            tbl_ListReservas.refresh();
        } else {
            System.out.println("Por favor completa todos los campos");
        }
    }

    /**
     * Método para eliminar una reservación luego de seleccionarla
     */
    @FXML
    private void eliminarReserva() {
        Reserva selectedReserva = tbl_ListReservas.getSelectionModel().getSelectedItem();
        if (selectedReserva != null) {
            reservas.remove(selectedReserva);
        } else {
            System.out.println("Seleccione una reserva para eliminar.");
        }
    }

    /**
     * Método para actualizar una reservación luego de seleccionarla
     */
    @FXML
    private void actualizarReserva() {
        Reserva selectedReserva = tbl_ListReservas.getSelectionModel().getSelectedItem();

        if (selectedReserva != null) {
            LocalDate fechaInicio = datePickerInicio.getValue();
            LocalDate fechaFin = datePickerFin.getValue();
            Cliente cliente = cb_Cliente.getValue();
            Vehiculo vehiculo = cb_Modelo.getValue();

            if (fechaInicio != null && fechaFin != null && cliente != null && vehiculo != null) {
                // Actualizar la reserva con los nuevos valores
                selectedReserva.setFechaInicio(fechaInicio);
                selectedReserva.setFechaFin(fechaFin);
                selectedReserva.setCliente(cliente);
                selectedReserva.setVehiculo(vehiculo);

                // Calcular los días entre las fechas
                int dias = (int) (fechaFin.toEpochDay() - fechaInicio.toEpochDay());

                // Calcular el valor de la reserva usando el método del vehículo
                double valorReserva = vehiculo.calcularTarifa(dias);
                selectedReserva.setValorReserva(valorReserva);

                // Actualizar la tabla
                tbl_ListReservas.refresh();
            } else {
                System.out.println("Por favor completa todos los campos.");
            }
        } else {
            System.out.println("Seleccione una reserva para actualizar.");
        }
    }

    /**
     * Método para calcular el valor de la reservación
     */
    @FXML
    private void calcularReserva() {
        LocalDate fechaInicio = datePickerInicio.getValue();
        LocalDate fechaFin = datePickerFin.getValue();
        if (fechaInicio != null && fechaFin != null) {
            double valor = calcularValorReserva(fechaInicio, fechaFin);
            System.out.println("El valor de la reserva es: " + valor);
        } else {
            System.out.println("Selecciona fechas de inicio y fin.");
        }
    }

    private double calcularValorReserva(LocalDate fechaInicio, LocalDate fechaFin) {
        long dias = fechaFin.toEpochDay() - fechaInicio.toEpochDay();
        return dias * 10000;
    }

    public void setClientes(ObservableList<Cliente> clientes) {
        listClientes.setAll(clientes);
        cb_Cliente.setItems(listClientes);
    }

    public void setVehiculos(ObservableList<Vehiculo> vehiculos) {
        cb_Modelo.setItems(vehiculos);
    }
    // -----------------------------------------------------------------------------------------------------------------------------------------------------
    // ---- Métodos para gestionar la vista de clientes ----

    private void initClienteView() {
        tbc_CedulaCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCedula()));
        tbc_NombreCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tbc_ApellidoCliente
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
        tbc_CorreoCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCorreo()));
        tbc_DireccionCliente
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion()));
        tbc_TelefonoCliente
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
        tbc_ClaveCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClave()));

        tbl_ListClientes.setItems(listClientes);

        listenerSelectionCliente();
    }

    private void initEmpleadoView() {
        tbc_CedulaEmpleado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCedula()));
        tbc_NombreEmpleado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tbc_ApellidoEmpleado
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
        tbc_CorreoEmpleado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCorreo()));
        tbc_DireccionEmpleado
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion()));
        tbc_TelefonoEmpleado
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
        tbc_ClaveEmpleado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClave()));
        tbc_SalarioEmpleado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSalario()));

        tbl_ListEmpleados.setItems(listEmpleados);

        listenerSelectionEmpleado();
    }

    private void obtenerClientes() {
        listClientes.addAll(App.clientes);
    }

    private void obtenerEmpleados() {
        listEmpleados.addAll(App.empleados);
    }

    private void listenerSelectionCliente() {
        tbl_ListClientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedCliente = newSelection;
            mostrarInformacionCliente(selectedCliente);
        });
    }

    private void listenerSelectionEmpleado() {
        tbl_ListEmpleados.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedEmpleado = newSelection;
            mostrarInformacionEmpleado(selectedEmpleado);
        });
    }

    private void mostrarInformacionCliente(Cliente cliente) {
        if (cliente != null) {
            txt_Cedula.setText(cliente.getCedula());
            txt_Nombre.setText(cliente.getNombre());
            txt_Apellido.setText(cliente.getApellido());
            txt_Correo.setText(cliente.getCorreo());
            txt_Direccion.setText(cliente.getDireccion());
            txt_Telefono.setText(cliente.getTelefono());
            txt_Clave.setText(cliente.getClave());
        }
    }

    private void mostrarInformacionEmpleado(Empleado empleado) {
        if (empleado != null) {
            txt_Cedula.setText(empleado.getCedula());
            txt_Nombre.setText(empleado.getNombre());
            txt_Apellido.setText(empleado.getApellido());
            txt_Correo.setText(empleado.getCorreo());
            txt_Direccion.setText(empleado.getDireccion());
            txt_Telefono.setText(empleado.getTelefono());
            txt_Clave.setText(empleado.getClave());
        }
    }

    @FXML
    private void agregarCliente() {
        Cliente cliente = buildCliente();
        if (empresaController.crearCliente(cliente)) {
            listClientes.add(cliente);
            limpiarCamposCliente();
        }
    }

    @FXML
    private void agregarEmpleado() {
        Empleado empleado = buildEmpleado();
        if (empresaController.crearEmpleado(empleado)) {
            listEmpleados.add(empleado);
            limpiarCamposEmpleado();
        }
    }

    private Cliente buildCliente() {
        Cliente cliente = new Cliente(txt_Cedula.getText(), txt_Nombre.getText(), txt_Apellido.getText(),
                txt_Telefono.getText(), txt_Correo.getText(), txt_Direccion.getText(), txt_Clave.getText());
        return cliente;
    }

    private Empleado buildEmpleado() {
        Empleado empleado = new Empleado(txt_CedulaEmpleado.getText(), txt_NombreEmpleado.getText(), txt_ApellidoEmpleado.getText(),
                txt_TelefonoEmpleado.getText(), txt_CorreoEmpleado.getText(), txt_DireccionEmpleado.getText(), txt_ClaveEmpleado.getText(),
                txt_SalarioEmpleado.getText());
        return empleado;
    }

    
    @FXML
    private void eliminarCliente() {
        if (empresaController.eliminarCliente(txt_Cedula.getText())) {
            listClientes.remove(selectedCliente);
            limpiarCamposCliente();
            limpiarSeleccion();
        }
    }
    @FXML
    private void eliminarEmpleado() {
        if (empresaController.eliminarEmpleado(txt_Cedula.getText())) {
            listEmpleados.remove(selectedEmpleado);
            limpiarCamposEmpleado();
            limpiarSeleccionEmpleado();
        }
    }

    @FXML
    private void actualizarCliente() {
        if (selectedCliente != null &&
                empresaController.actualizarCliente(selectedCliente.getCedula(), buildCliente())) {

            int index = listClientes.indexOf(selectedCliente);
            if (index >= 0) {
                listClientes.set(index, buildCliente());
            }

            tbl_ListClientes.refresh();
            limpiarSeleccion();
            limpiarCamposCliente();
        }
    }

    @FXML
    private void actualizarEmpleado() {
        if (selectedEmpleado != null &&
                empresaController.actualizarEmpleado(selectedEmpleado.getCedula(), buildEmpleado())) {

            int index = listEmpleados.indexOf(selectedEmpleado);
            if (index >= 0) {
                listEmpleados.set(index, buildEmpleado());
            }

            tbl_ListEmpleados.refresh();
            limpiarSeleccionEmpleado();
            limpiarCamposEmpleado();
        }
    }

    @FXML
    private void limpiarSeleccion() {
        tbl_ListClientes.getSelectionModel().clearSelection();
        limpiarCamposCliente();
    }

    @FXML
    private void limpiarSeleccionEmpleado() {
        tbl_ListEmpleados.getSelectionModel().clearSelection();
        limpiarCamposEmpleado();
    }

    private void limpiarCamposCliente() {
        txt_Cedula.clear();
        txt_Nombre.clear();
        txt_Apellido.clear();
        txt_Correo.clear();
        txt_Direccion.clear();
        txt_Clave.clear();
        txt_Telefono.clear();
    }

    private void limpiarCamposEmpleado() {
        txt_CedulaEmpleado.clear();
        txt_NombreEmpleado.clear();
        txt_ApellidoEmpleado.clear();
        txt_CorreoEmpleado.clear();
        txt_DireccionEmpleado.clear();
        txt_ClaveEmpleado.clear();
        txt_TelefonoEmpleado.clear();
        txt_SalarioEmpleado.clear();
    }

    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    private void volverAtras() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/primary.fxml"));
            AnchorPane loginPage = loader.load();
            Stage stage = (Stage) btn_Atras.getScene().getWindow();
            Scene scene = new Scene(loginPage);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}