package com.pds.controllers;

import com.pds.modelo.Curso;
import com.pds.modelo.Sesion;
import com.pds.modelo.Respuesta;
import com.pds.util.CargadorCursos;
import com.pds.util.GestorPersistencia;
import com.pds.util.PDSLoggerFactory;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import javafx.application.Platform;
import org.kordamp.ikonli.javafx.FontIcon;

public class MainController {
    
    private static final Logger logger = PDSLoggerFactory.getInstance().getLogger(MainController.class);
    private final GestorPersistencia gestorPersistencia = GestorPersistencia.getInstance();
    
    // Paneles principales
    @FXML private VBox courseSelectionPanel;
    @FXML private VBox strategySelectionPanel;
    @FXML private VBox questionPanel;
    
    // Componentes del panel de selección de curso
    @FXML private ListView<String> courseListView;
    @FXML private Button inspectButton;
    @FXML private Button startButton;
    @FXML private Button resumeButton;
    @FXML private Button flashcardsButton;
    @FXML private Button statisticsButton;
    
    // Componentes del panel de selección de estrategia
    @FXML private RadioButton sequentialStrategy;
    @FXML private RadioButton randomStrategy;
    @FXML private RadioButton spacedStrategy;
    @FXML private Button confirmStrategyButton;
    @FXML private Button cancelStrategyButton;
    
    // Componentes del panel de preguntas
    @FXML private Label progressLabel;
    @FXML private ProgressBar progressBar;
    @FXML private VBox questionContainer;
    @FXML private Button verifyButton;
    @FXML private Button exitButton;
    
    @FXML private Label statusLabel;
    
    private List<Curso> cursos;
    private Curso cursoSeleccionado;
    private int preguntaActual = 0;
    private int totalPreguntas = 0;
    
    private Sesion sesionActual;
    
    @FXML
    public void initialize() {
        // Crear y asignar el ToggleGroup para los RadioButton
        ToggleGroup strategyGroup = new ToggleGroup();
        sequentialStrategy.setToggleGroup(strategyGroup);
        randomStrategy.setToggleGroup(strategyGroup);
        spacedStrategy.setToggleGroup(strategyGroup);

        setupEventHandlers();
        loadCourses();
        showPanel(courseSelectionPanel);
    }
    
    private void setupEventHandlers() {
        // Inicialmente deshabilitados todos los botones excepto Inspeccionar
        startButton.setDisable(true);
        resumeButton.setDisable(true);
        flashcardsButton.setDisable(true);
        statisticsButton.setDisable(true);
        
        // Eventos de selección de curso
        courseListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                cursoSeleccionado = cursos.stream()
                    .filter(c -> c.getNombre().equals(newVal))
                    .findFirst()
                    .orElse(null);
                
                // Habilitar/deshabilitar botones según la selección
                boolean cursoValido = cursoSeleccionado != null;
                startButton.setDisable(!cursoValido);
                resumeButton.setDisable(!cursoValido);
                flashcardsButton.setDisable(!cursoValido);
                statisticsButton.setDisable(!cursoValido);
                
                if (cursoValido) {
                    statusLabel.setText("Curso seleccionado: " + cursoSeleccionado.getNombre());
                } else {
                    statusLabel.setText("Selecciona un curso");
                }
            }
        });
        
        // Eventos de botones principales
        startButton.setOnAction(e -> handleStartCourse());
        inspectButton.setOnAction(e -> handleInspectCourse());
        resumeButton.setOnAction(e -> handleResumeCourse());
        flashcardsButton.setOnAction(e -> handleFlashcards());
        statisticsButton.setOnAction(e -> handleStatistics());
        
        // Eventos de selección de estrategia
        confirmStrategyButton.setOnAction(e -> handleStrategyConfirmation());
        cancelStrategyButton.setOnAction(e -> showPanel(courseSelectionPanel));
        
        // Eventos de preguntas
        verifyButton.setOnAction(e -> handleVerifyAnswer());
        exitButton.setOnAction(e -> handleExit());
    }
    
    private void loadCourses() {
        try {
            CargadorCursos cargador = new CargadorCursos();
            cursos = cargador.cargarCursos();
            
            if (cursos.isEmpty()) {
                logger.error("No se encontraron cursos disponibles");
                mostrarErrorYAbortar("No se encontraron cursos disponibles");
                return;
            }
            
            ObservableList<String> nombresCursos = FXCollections.observableArrayList();
            for (Curso curso : cursos) {
                nombresCursos.add(curso.getNombre());
            }
            
            courseListView.setItems(nombresCursos);
            
            // Seleccionar automáticamente el primer curso
            if (!nombresCursos.isEmpty()) {
                courseListView.getSelectionModel().select(0);
                cursoSeleccionado = cursos.get(0);
                startButton.setDisable(false);
                resumeButton.setDisable(false);
                flashcardsButton.setDisable(false);
                statisticsButton.setDisable(false);
                statusLabel.setText("Curso seleccionado: " + cursoSeleccionado.getNombre());
            }
            
            logger.info("Cursos cargados: {}", cursos.size());
        } catch (Exception e) {
            logger.error("Error al cargar los cursos: {}", e.getMessage());
            mostrarErrorYAbortar("Error al cargar los cursos: " + e.getMessage());
        }
    }
    
    private void mostrarErrorYAbortar(String mensaje) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error crítico");
            alert.setContentText(mensaje);
            alert.showAndWait();
            Platform.exit();
        });
    }
    
    private void handleStartCourse() {
        if (cursoSeleccionado != null) {
            showPanel(strategySelectionPanel);
            statusLabel.setText("Selecciona una estrategia de aprendizaje");
        }
    }
    
    private void handleStrategyConfirmation() {
        String estrategia = "";
        if (sequentialStrategy.isSelected()) estrategia = "secuencial";
        else if (randomStrategy.isSelected()) estrategia = "aleatoria";
        else if (spacedStrategy.isSelected()) estrategia = "espaciada";
        
        if (!estrategia.isEmpty()) {
            // Crear nueva sesión
            sesionActual = new Sesion(cursoSeleccionado, estrategia);
            sesionActual = gestorPersistencia.guardarSesion(sesionActual);
            
            showPanel(questionPanel);
            iniciarSesion(estrategia);
        }
    }
    
    private void iniciarSesion(String estrategia) {
        if (cursoSeleccionado != null && !cursoSeleccionado.getBloques().isEmpty()) {
            var primerBloque = cursoSeleccionado.getBloques().get(0);
            totalPreguntas = primerBloque.getPreguntas().size();
            preguntaActual = 0;
            
            actualizarProgreso();
            mostrarPreguntaActual();
            
            statusLabel.setText("Sesión iniciada con estrategia: " + estrategia);
        }
    }
    
    private void mostrarPreguntaActual() {
        questionContainer.getChildren().clear();
        
        if (cursoSeleccionado != null && !cursoSeleccionado.getBloques().isEmpty()) {
            var bloque = cursoSeleccionado.getBloques().get(0);
            if (preguntaActual < bloque.getPreguntas().size()) {
                var pregunta = bloque.getPreguntas().get(preguntaActual);
                
                Label enunciadoLabel = new Label(pregunta.getEnunciado());
                enunciadoLabel.setWrapText(true);
                enunciadoLabel.setStyle("-fx-font-size: 16px;");
                
                VBox preguntaBox = new VBox(10);
                preguntaBox.getChildren().add(enunciadoLabel);
                
                if (pregunta.getTipo().equals("test")) {
                    var datos = (java.util.Map<String, Object>) pregunta.getDatos();
                    var opciones = (List<String>) datos.get("opciones");
                    
                    ToggleGroup grupo = new ToggleGroup();
                    for (String opcion : opciones) {
                        RadioButton radio = new RadioButton(opcion);
                        radio.setToggleGroup(grupo);
                        radio.setStyle("-fx-font-size: 14px;");
                        preguntaBox.getChildren().add(radio);
                    }
                }
                
                questionContainer.getChildren().add(preguntaBox);
            }
        }
    }
    
    private void actualizarProgreso() {
        progressLabel.setText(String.format("Pregunta %d de %d", preguntaActual + 1, totalPreguntas));
        progressBar.setProgress((double) (preguntaActual + 1) / totalPreguntas);
    }
    
    private void mostrarResumen() {
        if (sesionActual != null) {
            List<Respuesta> respuestas = sesionActual.getRespuestas();
            int totalCorrectas = (int) respuestas.stream().filter(Respuesta::isEsCorrecta).count();
            int totalIncorrectas = respuestas.size() - totalCorrectas;
            
            // Crear el contenido del resumen
            VBox contentBox = new VBox(15);
            
            // Título
            Label tituloLabel = new Label("Resumen del Bloque");
            tituloLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
            contentBox.getChildren().add(tituloLabel);
            
            // Estadísticas generales
            HBox statsBox = new HBox(20);
            VBox correctasBox = new VBox(5);
            FontIcon correctIcon = new FontIcon("mdi2c-check-circle");
            correctIcon.setStyle("-fx-font-size: 24px; -fx-text-fill: #4CAF50;");
            Label correctLabel = new Label(String.format("Correctas: %d", totalCorrectas));
            correctLabel.setStyle("-fx-font-size: 14px;");
            correctasBox.getChildren().addAll(correctIcon, correctLabel);
            
            VBox incorrectasBox = new VBox(5);
            FontIcon incorrectIcon = new FontIcon("mdi2c-close-circle");
            incorrectIcon.setStyle("-fx-font-size: 24px; -fx-text-fill: #f44336;");
            Label incorrectLabel = new Label(String.format("Incorrectas: %d", totalIncorrectas));
            incorrectLabel.setStyle("-fx-font-size: 14px;");
            incorrectasBox.getChildren().addAll(incorrectIcon, incorrectLabel);
            
            statsBox.getChildren().addAll(correctasBox, incorrectasBox);
            contentBox.getChildren().add(statsBox);
            
            // Detalle de respuestas incorrectas
            if (totalIncorrectas > 0) {
                Label detalleLabel = new Label("Detalle de respuestas incorrectas:");
                detalleLabel.setStyle("-fx-font-weight: bold;");
                contentBox.getChildren().add(detalleLabel);
                
                VBox detalleBox = new VBox(10);
                for (Respuesta respuesta : respuestas) {
                    if (!respuesta.isEsCorrecta()) {
                        HBox respuestaBox = new HBox(10);
                        Label preguntaLabel = new Label(String.format("Pregunta %d:", respuesta.getNumeroPregunta() + 1));
                        preguntaLabel.setStyle("-fx-font-weight: bold;");
                        Label respuestaLabel = new Label(String.format("Tu respuesta: %s | Correcta: %s",
                            respuesta.getRespuestaUsuario(),
                            respuesta.getRespuestaCorrecta()));
                        respuestaBox.getChildren().addAll(preguntaLabel, respuestaLabel);
                        detalleBox.getChildren().add(respuestaBox);
                    }
                }
                contentBox.getChildren().add(detalleBox);
            }
            
            // Porcentaje de aciertos
            double porcentaje = (double) totalCorrectas / respuestas.size() * 100;
            Label porcentajeLabel = new Label(String.format("Porcentaje de aciertos: %.1f%%", porcentaje));
            porcentajeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            contentBox.getChildren().add(porcentajeLabel);
            
            // Mostrar el diálogo
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Resumen del Bloque");
            alert.setHeaderText(null);
            alert.getDialogPane().setContent(contentBox);
            alert.getDialogPane().setPrefWidth(500);
            alert.showAndWait();
        }
    }
    
    private void handleVerifyAnswer() {
        if (cursoSeleccionado != null && !cursoSeleccionado.getBloques().isEmpty()) {
            var bloque = cursoSeleccionado.getBloques().get(0);
            if (preguntaActual < bloque.getPreguntas().size()) {
                var pregunta = bloque.getPreguntas().get(preguntaActual);
                
                if (pregunta.getTipo().equals("test")) {
                    var datos = (java.util.Map<String, Object>) pregunta.getDatos();
                    String respuestaCorrecta = (String) datos.get("respuestaCorrecta");
                    
                    // Buscar la respuesta seleccionada
                    String respuestaSeleccionada = null;
                    for (var node : questionContainer.getChildren()) {
                        if (node instanceof VBox) {
                            for (var child : ((VBox) node).getChildren()) {
                                if (child instanceof RadioButton && ((RadioButton) child).isSelected()) {
                                    respuestaSeleccionada = ((RadioButton) child).getText();
                                    break;
                                }
                            }
                        }
                    }
                    
                    if (respuestaSeleccionada != null) {
                        boolean esCorrecta = respuestaSeleccionada.equals(respuestaCorrecta);
                        
                        // Guardar la respuesta
                        Respuesta respuesta = new Respuesta(preguntaActual, respuestaSeleccionada, respuestaCorrecta);
                        sesionActual.addRespuesta(respuesta);
                        gestorPersistencia.guardarRespuesta(respuesta);
                        
                        Alert alert;
                        if (esCorrecta) {
                            alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Resultado");
                            alert.setHeaderText(null);
                            alert.setContentText("¡Correcto!");
                        } else {
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Resultado");
                            alert.setHeaderText(null);
                            
                            // Crear el contenido con formato
                            VBox contentBox = new VBox(10);
                            contentBox.getChildren().add(new Label("¡Incorrecto!"));
                            contentBox.getChildren().add(new Label("")); // Línea en blanco
                            contentBox.getChildren().add(new Label("La respuesta correcta es:"));
                            contentBox.getChildren().add(new Label(respuestaCorrecta));
                            
                            alert.getDialogPane().setContent(contentBox);
                        }
                        
                        alert.showAndWait();
                        
                        // Avanzar a la siguiente pregunta
                        if (preguntaActual < totalPreguntas - 1) {
                            preguntaActual++;
                            sesionActual.setPreguntaActual(preguntaActual);
                            gestorPersistencia.actualizarSesion(sesionActual);
                            actualizarProgreso();
                            mostrarPreguntaActual();
                        } else {
                            // Última pregunta
                            sesionActual.setCompletada(true);
                            gestorPersistencia.actualizarSesion(sesionActual);
                            
                            // Mostrar resumen
                            mostrarResumen();
                            
                            handleExit();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Atención");
                        alert.setHeaderText(null);
                        alert.setContentText("Por favor, selecciona una respuesta.");
                        alert.showAndWait();
                    }
                }
            }
        }
    }
    
    private void handleExit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Salir");
        alert.setHeaderText(null);
        alert.setContentText("¿Estás seguro de que quieres salir? Tu progreso será guardado.");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (sesionActual != null && !sesionActual.isCompletada()) {
                    sesionActual.setPreguntaActual(preguntaActual);
                    gestorPersistencia.actualizarSesion(sesionActual);
                }
                showPanel(courseSelectionPanel);
                statusLabel.setText("Sesión guardada");
            }
        });
    }
    
    private void handleInspectCourse() {
        if (cursoSeleccionado != null) {
            VBox contentBox = new VBox(15);
            
            Label titleLabel = new Label(cursoSeleccionado.getNombre());
            titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
            contentBox.getChildren().add(titleLabel);
            
            Label descLabel = new Label("Descripción:");
            descLabel.setStyle("-fx-font-weight: bold;");
            Label descContent = new Label(cursoSeleccionado.getDescripcion());
            descContent.setWrapText(true);
            contentBox.getChildren().addAll(descLabel, descContent);
            
            Label blocksLabel = new Label("Bloques de contenido:");
            blocksLabel.setStyle("-fx-font-weight: bold;");
            contentBox.getChildren().add(blocksLabel);
            
            VBox blocksBox = new VBox(5);
            for (int i = 0; i < cursoSeleccionado.getBloques().size(); i++) {
                var bloque = cursoSeleccionado.getBloques().get(i);
                HBox blockInfo = new HBox(10);
                Label blockNum = new Label(String.format("Bloque %d:", i + 1));
                blockNum.setStyle("-fx-font-weight: bold;");
                Label blockName = new Label(bloque.getTitulo());
                Label questionCount = new Label(String.format("(%d preguntas)", bloque.getPreguntas().size()));
                blockInfo.getChildren().addAll(blockNum, blockName, questionCount);
                blocksBox.getChildren().add(blockInfo);
            }
            contentBox.getChildren().add(blocksBox);
            
            int totalPreguntas = cursoSeleccionado.getBloques().stream()
                .mapToInt(b -> b.getPreguntas().size())
                .sum();
            Label totalLabel = new Label(String.format("Total de preguntas: %d", totalPreguntas));
            totalLabel.setStyle("-fx-font-weight: bold;");
            contentBox.getChildren().add(totalLabel);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información del Curso");
            alert.setHeaderText(null);
            alert.getDialogPane().setContent(contentBox);
            alert.getDialogPane().setPrefWidth(400);
            alert.showAndWait();
        }
    }
    
    private void handleResumeCourse() {
        if (cursoSeleccionado != null) {
            List<Sesion> sesionesIncompletas = gestorPersistencia.buscarSesionesIncompletas(cursoSeleccionado.getNombre());
            
            if (sesionesIncompletas.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText(null);
                alert.setContentText("No hay sesiones incompletas para este curso.");
                alert.showAndWait();
                return;
            }
            
            // Usar la sesión más reciente
            sesionActual = sesionesIncompletas.get(0);
            preguntaActual = sesionActual.getPreguntaActual();
            totalPreguntas = sesionActual.getTotalPreguntas();
            
            showPanel(questionPanel);
            actualizarProgreso();
            mostrarPreguntaActual();
            
            statusLabel.setText("Sesión reanudada: " + sesionActual.getEstrategia());
        }
    }
    
    private void handleFlashcards() {
        // TODO: Implementar CU04 - Flashcards
    }
    
    private void handleStatistics() {
        // TODO: Implementar CU05 - Consultar estadísticas
    }
    
    private void showPanel(VBox panel) {
        courseSelectionPanel.setVisible(panel == courseSelectionPanel);
        strategySelectionPanel.setVisible(panel == strategySelectionPanel);
        questionPanel.setVisible(panel == questionPanel);
    }
} 