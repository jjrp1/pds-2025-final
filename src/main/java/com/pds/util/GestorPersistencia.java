package com.pds.util;

import com.pds.modelo.Sesion;
import com.pds.modelo.Respuesta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class GestorPersistencia {
    
    private static final String PERSISTENCE_UNIT = "pds-persistence";
    private static GestorPersistencia instance;
    private final EntityManagerFactory emf;
    
    private GestorPersistencia() {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
    }
    
    public static synchronized GestorPersistencia getInstance() {
        if (instance == null) {
            instance = new GestorPersistencia();
        }
        return instance;
    }
    
    public Sesion guardarSesion(Sesion sesion) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(sesion);
            em.getTransaction().commit();
            return sesion;
        } finally {
            em.close();
        }
    }
    
    public void actualizarSesion(Sesion sesion) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(sesion);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    public Optional<Sesion> buscarSesionPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return Optional.ofNullable(em.find(Sesion.class, id));
        } finally {
            em.close();
        }
    }
    
    public List<Sesion> buscarSesionesIncompletas(String nombreCurso) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Sesion> query = em.createQuery(
                "SELECT s FROM Sesion s WHERE s.nombreCurso = :nombreCurso AND s.completada = false",
                Sesion.class
            );
            query.setParameter("nombreCurso", nombreCurso);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public void guardarRespuesta(Respuesta respuesta) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(respuesta);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    public void cerrar() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
} 