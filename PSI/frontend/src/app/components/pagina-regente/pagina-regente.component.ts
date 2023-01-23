import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Docente } from 'src/app/models/docente.model';
import { DocenteService } from '../../services/docente.service';


@Component({
  selector: 'app-pagina-regente',
  templateUrl: './pagina-regente.component.html',
  styleUrls: ['./pagina-regente.component.css']
})
export class PaginaRegenteComponent implements OnInit {


  constructor(private docenteService : DocenteService, private router: Router, private route: ActivatedRoute) { }

  id;
  nomeDocente;
  podeVigiar;

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = params.id;
      this.docenteService.getDocente(this.id).subscribe((docente: Docente) => {
        this.nomeDocente = docente.nome;
        if (docente.sabatica) {
          this.podeVigiar = "Este docente está de sabática";
        }
        else {
          if (docente.responsavelGestao) {
            this.podeVigiar = "Este docente é responsável de gestão";
          }
          else {
            if (docente.categoria === "catedratico") {
              this.podeVigiar = "Este docente é catedrático logo não pode vigiar exames";
            }
            else{
              if(docente.categoria === "associado"){
                this.podeVigiar = "Este docente é associado logo não pode vigiar exames";
              }
              else{
                this.podeVigiar = "Este docente pode vigiar exames!";
              }
            }
          }
        }
      });
      
    });
  }

  checkMeusExames() {
  }

  checkVigilancias(){
    this.router.navigate([`/listaVigilancias/${this.id}`]);
  }

  checkVigilanciasDocente() {
    this.router.navigate([`/paginaVigilanciasDocente/${this.id}`]);
  }
}
