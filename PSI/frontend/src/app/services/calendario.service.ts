import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class CalendarioService {

  uri = 'http://localhost:27017'

  constructor(private http: HttpClient) { }

  createCalendario() {

  }
}
