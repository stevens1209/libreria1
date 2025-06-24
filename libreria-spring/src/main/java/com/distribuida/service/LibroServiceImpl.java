package com.distribuida.service;
import com.distribuida.dao.LibroRepositorio;
import com.distribuida.model.Libro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroServiceImpl implements LibroService {

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Override
    public List<Libro> findAll() {
        return libroRepositorio.findAll();
    }

    @Override
    public Libro findOne(int id) {
        Optional<Libro> libro = libroRepositorio.findById(id);
        return libro.orElse(null);
    }

    @Override
    public Libro save(Libro libro) {
        return libroRepositorio.save(libro);
    }

    @Override
    public Libro update(int id, Libro libro) {
        Libro libroExistente = findOne(id);
        if (libroExistente == null) {
            return null;
        }

        libroExistente.setTitulo(libro.getTitulo());
        libroExistente.setEditorial(libro.getEditorial());
        libroExistente.setNumPaginas(libro.getNumPaginas());
        libroExistente.setEdicion(libro.getEdicion());
        libroExistente.setIdioma(libro.getIdioma());
        libroExistente.setFechaPublicacion(libro.getFechaPublicacion());
        libroExistente.setDescripcion(libro.getDescripcion());
        libroExistente.setTipoPasta(libro.getTipoPasta());
        libroExistente.setIsbn(libro.getIsbn());
        libroExistente.setNumEjemplares(libro.getNumEjemplares());
        libroExistente.setPortada(libro.getPortada());
        libroExistente.setPresentacion(libro.getPresentacion());
        libroExistente.setPrecio(libro.getPrecio());
        libroExistente.setCategoria(libro.getCategoria());
        libroExistente.setAutor(libro.getAutor());

        return libroRepositorio.save(libroExistente);
    }

    @Override
    public void delete(int id) {
        if (libroRepositorio.existsById(id)) {
            libroRepositorio.deleteById(id);
        }
    }
}