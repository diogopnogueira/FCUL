import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TrocasService {


  uri = 'http://appserver.alunos.di.fc.ul.pt:3018'

  constructor(private http: HttpClient) { }

  getTroca(id) {
    return this.http.get(`${this.uri}/trocas/${id}`);
  }

  getTrocas() {
    return this.http.get(`${this.uri}/trocas`);
  }

  newTroca(docenteID: string, exameID: string) {
    const troca = {
      vigilanteID: docenteID,
      exameID : exameID
    };
    return this.http.post(`${this.uri}/trocas/add`, troca).subscribe();
  }

  deleteTroca(id) {
    return this.http.get(`${this.uri}/trocas/delete/${id}`);
  }

}


