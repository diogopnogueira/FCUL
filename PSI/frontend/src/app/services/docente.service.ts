import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DocenteService {


  uri = 'http://localhost:27017/3018'

  constructor(private http: HttpClient) { }

  getDocente(id) {
    return this.http.get(`${this.uri}/docentes/${id}`);
  }

  newDocente(id, nome, departamento, regente, regencias, sabatica, categoria, responsavelGestao, unidadesLecionadas){
    const docente = {
      nome: nome,
      departamento: departamento,
      regente: regente,
      regencias: regencias,
      sabatica: sabatica,
      categoria: categoria,
      responsavelGestao: responsavelGestao,
      numeroVigilancias: 0,
      listaVigilancias: [],
      unidadesLecionadas: unidadesLecionadas
    };
    return this.http.post(`${this.uri}/docentes/add/${id}`, docente).subscribe();
  }

  getAllDocentes(){
    return this.http.get(`${this.uri}/docentes`);
  }

  buscaDocentes(){
    return this.http.get(`${this.uri}/docentes/sreg`);
  }

  buscaRegentes(){
    return this.http.get(`${this.uri}/docentes/regente`);
  }

  updateDocente(id, nome: string, departamento: string, regente: boolean, regencias: string[],
      sabatica: boolean, categoria: string, responsavelGestao: boolean, numeroVigilancias: number,
      listaVigilancias: string[], unidadesLecionadas: string[]) {
    const docente = {
      nome: nome,
      departamento: departamento,
      regente: regente,
      regencias: regencias,
      sabatica: sabatica,
      categoria: categoria,
      responsavelGestao: responsavelGestao,
      numeroVigilancias: numeroVigilancias,
      listaVigilancias: listaVigilancias,
      unidadesLecionadas: unidadesLecionadas
    };
    return this.http.post(`${this.uri}/docentes/update/${id}`, docente).subscribe();
  }

  deleteDocente(id) {
    return this.http.get(`${this.uri}/docentes/delete/${id}`).subscribe();
  }

  getLeastVigilancia() {
    return this.http.get(`${this.uri}/docentes/getLeastVigilancia`);
  }

  meteVig(id, numero, vig){
    const docente = {
      num: numero,
      vig: vig
    }
    return this.http.post(`${this.uri}/docentes/metevig/${id}`, docente).subscribe();
  }
}


