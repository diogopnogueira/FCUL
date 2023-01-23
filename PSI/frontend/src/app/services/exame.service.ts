import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ExameService {



  uri = 'http://appserver.alunos.di.fc.ul.pt/psi018'

  constructor(private http: HttpClient) { }

  getVigilancia(id) {
    return this.http.get(`${this.uri}/exames/${id}`);
  }

  getExames() {
    return this.http.get(`${this.uri}/exames`);
  }

  newExame(codigo, disciplina, semestre, epoca, dataInicio, dataFim, duracao, salas, cursos, numeroVigilantes) {

    const exame = {
      codigo: codigo,
      disciplina: disciplina,
      semestre: semestre,
      epoca: epoca,
      dataInicio: dataInicio,
      dataFim: dataFim,
      duracao: duracao,
      salas: salas,
      cursos: cursos,
      numeroVigilantes: numeroVigilantes,
    };

    return this.http.post(`${this.uri}/exames/add`, exame).subscribe();
  }

  updateExame(id, codigo, disciplina, semestre, epoca, dataInicio,
    dataFim, duracao, salas, cursos, numeroVigilantes, vigilancias) {
    const exame = {
      codigo: codigo,
      disciplina: disciplina,
      semestre: semestre,
      epoca: epoca,
      dataInicio: dataInicio,
      dataFim: dataFim,
      duracao: duracao,
      salas: salas,
      cursos: cursos,
      numeroVigilantes: numeroVigilantes,
      vigilantes: vigilancias
    };
    return this.http.post(`${this.uri}/exames/update/${id}`, exame).subscribe();
  }

  deleteExame(id) {
    return this.http.get(`${this.uri}/exames/delete/${id}`).subscribe();
  }

  atribuiExames(idE,number: number) {
    const body = {
      number: number
    }
    return this.http.post(`${this.uri}/exames/atribui/${idE}`, body).subscribe();
  }

  getVigilanciasbyDocente1s(id: any) {
    return this.http.get(`${this.uri}/exames/vigilanciasbydocente1s/${id}`);
  }
  getVigilanciasbyDocente2s(id: any) {
    return this.http.get(`${this.uri}/exames/vigilanciasbydocente2s/${id}`);
  }
  getVigilanciasbyNumeroVigilancias(id: any) {
    return this.http.get(`${this.uri}('/exames/numeroVigs/${id}`);
  }

}


