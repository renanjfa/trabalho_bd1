package com.featurestore.featurestore.service;

import com.featurestore.featurestore.dao.DAOFactory;
import com.featurestore.featurestore.dao.DownloadVersaoDAO;
import com.featurestore.featurestore.models.DownloadVersao;

import java.sql.Date;
import java.sql.Time;
import java.util.List;


public class DownloadVersaoService {

    public void registrarDownload(String emailUsuario, Integer idVersao) throws Exception {
        if (emailUsuario == null || emailUsuario.isBlank()){
            throw new Exception("Email do usuario é obrigatorio");
        }
        if(idVersao == null){
            throw new Exception("ID da versao é obrigatorio");
        }
        DownloadVersao download = new DownloadVersao();
        download.setEmailUsuario(emailUsuario);
        download.setIdVersao(idVersao);
        download.setData(new Date(System.currentTimeMillis()));
        download.setHora(new Time(System.currentTimeMillis()));

        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
            DownloadVersaoDAO dao = daoFactory.getDownloadVersaoDAO();
            dao.create(download);
        }
    }

    public List<DownloadVersao> listarPorVersao(Integer idVersao) throws Exception {
        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
            DownloadVersaoDAO dao = daoFactory.getDownloadVersaoDAO();
            return dao.getByIdVersao(idVersao);
        }
    }

    public List<DownloadVersao> listarPorUsuario(String email) throws Exception {
        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
            DownloadVersaoDAO dao = daoFactory.getDownloadVersaoDAO();
            return dao.getByEmailUsuario(email);
        }
    }
    
}
