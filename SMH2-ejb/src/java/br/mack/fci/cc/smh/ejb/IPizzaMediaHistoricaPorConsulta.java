/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.mack.fci.cc.smh.ejb;

/**
 *
 * @author prgoes
 */
public interface IPizzaMediaHistoricaPorConsulta {

    PizzaDTO executarPorConsulta(String nomeConsulta);
    
}