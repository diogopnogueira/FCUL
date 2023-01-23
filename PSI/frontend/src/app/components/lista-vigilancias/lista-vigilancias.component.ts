import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { Exame } from '../../models/exame.model';
import { Router, ActivatedRoute } from '@angular/router';
import { DocenteService } from '../../services/docente.service';
import { ExameService } from '../../services/exame.service';
import { Docente } from '../../models/docente.model';
import { TrocasService } from 'src/app/services/trocas.service';


export interface VigilanciasDisplay {
  idExame: String,
  codigo: Number,
  disciplina: String,
  epoca: String,
  dataExame: String,
  salas: String,
}

@Component({
  selector: 'app-lista-vigilancias',
  templateUrl: './lista-vigilancias.component.html',
  styleUrls: ['./lista-vigilancias.component.css']
})



export class ListaVigilanciasComponent implements OnInit {

  selectedAno = "none";
  id: string;
  vigilancias1: Exame[];
  vigilancias2: Exame[];
  vigilancias1Bom: VigilanciasDisplay[] = [];
  vigilancias2Bom: VigilanciasDisplay[] = [];
  examesResponsavel: Exame[];
  public vigilanciasBoas = new MatTableDataSource();
  docente: Docente;
  exame: Exame;
  displayedColumns = ['codigo', 'disciplina', 'epoca',
    'dataExame', 'salas', 'acoes'];

  constructor(private docenteService: DocenteService, private exameService: ExameService,
    private router: Router, private trocasService: TrocasService, private route: ActivatedRoute) { }


  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngAfterViewInit() {
    this.vigilanciasBoas.paginator = this.paginator;
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = params.id;
      this.fetchVigilancias(this.id);
    });
  }

  fetchVigilanciasBoas(semestre) {
    if (semestre === 1) {
      this.vigilanciasBoas.data = this.vigilancias1Bom;
    }
    else {
      this.vigilanciasBoas.data = this.vigilancias2Bom;
    }
    console.log(this.vigilanciasBoas);
  }

  fetchVigilancias(id) {
    this.docenteService.getDocente(id).subscribe((data: Docente) => {
      this.docente = data;
      this.vigilancias2 = [];
      this.exameService.getVigilanciasbyDocente1s(id).subscribe((data: Exame[]) => {
        this.vigilancias1 = data;
        this.vigilancias1.forEach(data2 => {

          this.exame = data2;
          var dataInicio = new Date();
          dataInicio = this.exame.dataInicio;

          //POR A DATA EM FORMATO CERTO
          var data = new Date(dataInicio);
          var ano = data.getFullYear();
          var mes = data.getMonth();
          var dia = data.getDate();
          var diaInt = +dia;
          var diaCerto;
          if (diaInt < 10) {
            diaCerto = "0" + dia;
          }
          else {
            diaCerto = dia;
          }
          var mete = diaCerto + " - 0" + mes + " - " + ano;
          //////////////////////////////

          var exameEmObject = Object.values(data2);
          console.log(exameEmObject);
          var idDoExame = exameEmObject[2];
          const vigilancia = {
            idExame: idDoExame,
            codigo: this.exame.codigo,
            disciplina: this.exame.disciplina,
            epoca: this.exame.epoca,
            dataExame: mete,
            salas: this.exame.salas,
          }
          this.vigilancias1Bom.push(vigilancia);

        });
      });
      this.exameService.getVigilanciasbyDocente2s(id).subscribe((data: Exame[]) => {
        this.vigilancias2 = data;
        this.vigilancias2.forEach(data2 => {

          this.exame = data2;
          var dataInicio = new Date();
          dataInicio = this.exame.dataInicio;

          //POR A DATA EM FORMATO CERTO
          var data = new Date(dataInicio);
          var ano = data.getFullYear();
          var mes = data.getMonth();
          var dia = data.getDate();
          var diaInt = +dia;
          var diaCerto;
          if (diaInt < 10) {
            diaCerto = "0" + dia;
          }
          else {
            diaCerto = dia;
          }
          var mete = diaCerto + " - 0" + mes + " - " + ano;
          //////////////////////////////

          var exameEmObject = Object.values(data2);
          console.log(exameEmObject);
          var idDoExame = exameEmObject[2];

          const vigilancia = {
            idExame: idDoExame,
            codigo: this.exame.codigo,
            disciplina: this.exame.disciplina,
            epoca: this.exame.epoca,
            dataExame: mete,
            salas: this.exame.salas,
          }
          this.vigilancias2Bom.push(vigilancia);


        });
      });

    });
  }

  fetchExamesResponsavel(id) {
    this.docenteService.getDocente(id)
      .subscribe((data: Docente) => {
        this.docente = data;
        this.examesResponsavel = [];
        for (let index = 0; index < this.docente.regencias.length; index++) {
          const element = this.docente.regencias[index];
          this.exameService.getVigilancia(element)
            .subscribe((data2: Exame) => {
              this.examesResponsavel.push(data2);
            });
        }
      });
  }

  pedirTroca(idDocente, idExame) {
    this.trocasService.newTroca(idDocente, idExame);
  }

  voltaAtras() {
    this.docenteService.getDocente(this.id).subscribe((docente: Docente) => {
      if (docente.regente) {
        this.router.navigate([`/paginaRegente/${this.id}`]);
      } else {
        this.router.navigate([`/paginaDocente/${this.id}`]);
      }
    });
  }

}
