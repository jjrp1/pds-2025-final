package com.pds.util;

import com.pds.modelo.Curso;
import com.pds.modelo.Bloque;
import com.pds.modelo.PreguntaBase;
import com.pds.core.GestorPluginsPreguntas;
import com.pds.core.Pregunta;
import org.slf4j.Logger;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.LoaderOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CargadorCursos {
    private final GestorPluginsPreguntas gestorPlugins;
    private final File directorioCursos;
    private final Logger logger;
    
    public CargadorCursos() {
        this.gestorPlugins = GestorPluginsPreguntas.getInstancia();
        this.directorioCursos = new File("cursos");
        this.logger = PDSLoggerFactory.getInstance().getLogger(CargadorCursos.class);
        
        logger.info("Inicializando CargadorCursos. Directorio de cursos: {}", directorioCursos.getAbsolutePath());
    }
    
    public List<Curso> cargarCursos() {
        List<Curso> cursos = new ArrayList<>();
        if (!directorioCursos.exists() || !directorioCursos.isDirectory()) {
            logger.error("El directorio de cursos no existe o no es un directorio: {}", directorioCursos.getAbsolutePath());
            return cursos;
        }
        
        File[] carpetasCursos = directorioCursos.listFiles(File::isDirectory);
        if (carpetasCursos == null) {
            logger.error("No se pudo listar el contenido del directorio de cursos");
            return cursos;
        }
        
        logger.info("Encontradas {} carpetas de cursos", carpetasCursos.length);
        
        for (File carpetaCurso : carpetasCursos) {
            File archivoYaml = new File(carpetaCurso, carpetaCurso.getName() + ".yaml");
            if (archivoYaml.exists()) {
                try {
                    logger.debug("Cargando curso desde: {}", archivoYaml.getAbsolutePath());
                    Curso curso = cargarCursoFlexible(archivoYaml);
                    if (curso != null) {
                        cursos.add(curso);
                        logger.info("Curso cargado exitosamente: {}", curso.getNombre());
                    }
                } catch (Exception e) {
                    logger.error("Error cargando curso: {}", archivoYaml.getName(), e);
                }
            } else {
                logger.warn("No se encontró el archivo YAML en la carpeta: {}", carpetaCurso.getAbsolutePath());
            }
        }
        
        logger.info("Total de cursos cargados: {}", cursos.size());
        return cursos;
    }

    private Curso cargarCursoFlexible(File archivoYaml) throws Exception {
        Yaml yaml = new Yaml(new SafeConstructor(new LoaderOptions()));
        try (InputStream input = new FileInputStream(archivoYaml)) {
            logger.debug("Leyendo archivo YAML: {}", archivoYaml.getName());
            Map<String, Object> data = yaml.load(input);
            
            Curso curso = new Curso();
            curso.setId((String) data.get("id"));
            curso.setNombre((String) data.get("nombre"));
            curso.setDescripcion((String) data.get("descripcion"));
            
            logger.debug("Procesando curso: {} ({})", curso.getNombre(), curso.getId());
            
            List<Bloque> bloques = new ArrayList<>();
            List<Map<String, Object>> bloquesYaml = (List<Map<String, Object>>) data.get("bloques");
            if (bloquesYaml != null) {
                logger.debug("Encontrados {} bloques en el curso", bloquesYaml.size());
                for (Map<String, Object> bloqueYaml : bloquesYaml) {
                    Bloque bloque = new Bloque();
                    bloque.setId((String) bloqueYaml.get("id"));
                    bloque.setTitulo((String) bloqueYaml.get("titulo"));
                    bloque.setTipo((String) bloqueYaml.get("tipo"));
                    
                    logger.debug("Procesando bloque: {} ({})", bloque.getTitulo(), bloque.getId());
                    
                    List<PreguntaBase> preguntas = new ArrayList<>();
                    List<Map<String, Object>> preguntasYaml = (List<Map<String, Object>>) bloqueYaml.get("preguntas");
                    if (preguntasYaml != null) {
                        logger.debug("Encontradas {} preguntas en el bloque", preguntasYaml.size());
                        for (Map<String, Object> preguntaYaml : preguntasYaml) {
                            PreguntaBase pregunta = new PreguntaBase();
                            pregunta.setId((String) preguntaYaml.get("id"));
                            pregunta.setTipo((String) preguntaYaml.get("tipo"));
                            pregunta.setEnunciado((String) preguntaYaml.get("enunciado"));
                            
                            Map<String, Object> datos = new HashMap<>();
                            for (Map.Entry<String, Object> entry : preguntaYaml.entrySet()) {
                                String key = entry.getKey();
                                if (!key.equals("id") && !key.equals("tipo") && !key.equals("enunciado")) {
                                    datos.put(key, entry.getValue());
                                }
                            }
                            pregunta.setDatos(datos);
                            preguntas.add(pregunta);
                            
                            logger.debug("Procesada pregunta: {} ({})", pregunta.getId(), pregunta.getTipo());
                        }
                    }
                    bloque.setPreguntas(preguntas);
                    bloques.add(bloque);
                }
            }
            curso.setBloques(bloques);
            return curso;
        }
    }
} 