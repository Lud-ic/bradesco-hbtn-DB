package models;

import entities.Aluno;
import entities.Curso;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class CursoModel {
    public void create(Curso curso) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestao-cursos");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(curso);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
            System.out.println("Finalizando a transação");
        }
    }

    public Curso findById(Long id){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestao-cursos");
        EntityManager em = emf.createEntityManager();
        Curso curso = null;
        try {
            curso = em.find(Curso.class, id);
        }catch (Exception e){
            em.close();
            System.err.println("Erro ao buscar curso por ID: " + e.getMessage());
        } finally {
            em.close();
            System.out.println("Finalizando a transação");
        }
        return curso;
    }

    public List<Curso> findAll(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestao-cursos");
        EntityManager em = emf.createEntityManager();
        List<Curso> cursos = new java.util.ArrayList<>();
        try {
            cursos = em.createQuery("SELECT c FROM Curso c", Curso.class).getResultList();
        } catch (Exception e) {
            System.err.println("Erro ao buscar todos os cursos: " + e.getMessage());
        } finally {
            em.close();
            System.out.println("Finalizando a transação");
        }
        return cursos;
    }

    public void update(Curso curso){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestao-cursos");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(curso);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
            System.out.println("Finalizando a transação");
        }
    }

    public void delete(Curso curso){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestao-cursos");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Curso managed = em.contains(curso) ? curso : em.merge(curso);
            em.remove(managed);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
            System.out.println("Finalizando a transação");
        }
    }
}
