package bean;

 

import java.io.Serializable;

 

import javax.xml.bind.annotation.XmlAccessType;

import javax.xml.bind.annotation.XmlAccessorType;

import javax.xml.bind.annotation.XmlElement;

import javax.xml.bind.annotation.XmlRootElement;

 

@XmlRootElement

@XmlAccessorType(XmlAccessType.FIELD)

public class UF implements Serializable {

 

        private static final long serialVersionUID = 1L;

 

        @XmlElement(name = "nome")

        private String nome;

       

        @XmlElement(name = "capital")

        private String capital;

       

        @XmlElement(name = "habitantes")

        private Integer habitantes;

       

        public UF() {

        }

 

        public UF(String nome, String capital, String habitantes) {

                super();

                this.nome = nome;

                this.capital = capital;

                this.habitantes = Integer.parseInt(habitantes);

        }

 

        public String getNome() {

                return nome;

        }

 

        public String getCapital() {

                return capital;

        }

 

        public Integer getHabitantes() {

                return habitantes;

        }      

}