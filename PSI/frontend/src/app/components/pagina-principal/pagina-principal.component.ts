import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { DocenteService } from 'src/app/services/docente.service';
import { Docente } from 'src/app/models/docente.model';


@Component({
  selector: 'app-pagina-principal',
  templateUrl: './pagina-principal.component.html',
  styleUrls: ['./pagina-principal.component.css']
})
export class PaginaPrincipalComponent implements OnInit {

  docentes: Docente[];
  regentes: Docente[];

  constructor(private docenteService : DocenteService, private fb: FormBuilder, private router: Router) {
   }

  ngOnInit() {
    this.fetchDocentes();
    this.fetchRegentes();
  }

  checkVigilancias(id) {
    this.router.navigate([`/paginaDocente/${id}`]);
  }

  checkRegente(id) {
    this.router.navigate([`/paginaRegente/${id}`]);
  }

  fetchDocentes(){
    this.docenteService.buscaDocentes().subscribe((data: Docente[]) => {
      this.docentes = [];
      this.docentes = data;
    });
  }

  fetchRegentes(){
    this.docenteService.buscaRegentes().subscribe((data: Docente[]) => {
      this.regentes = [];
      this.regentes = data;
    });
  }
}
