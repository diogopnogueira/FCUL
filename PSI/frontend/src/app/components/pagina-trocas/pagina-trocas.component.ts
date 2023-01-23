import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { Router, ActivatedRoute } from '@angular/router';
import { Troca } from '../../models/troca.model';
import { Exame } from '../../models/exame.model';
import { Docente } from '../../models/docente.model';
import { TrocasService } from '../../services/trocas.service';
import { ExameService } from '../../services/exame.service';
import { DocenteService } from '../../services/docente.service';


export interface TrocaDisplay {
  nomeDocente : String,
  nomeExame : String,
  diaExame : String;
}


@Component({
  selector: 'app-pagina-trocas',
  templateUrl: './pagina-trocas.component.html',
  styleUrls: ['./pagina-trocas.component.css']
})
export class PaginaTrocasComponent implements OnInit {

  arrayTrocas : TrocaDisplay[];
  public arrayTrocasBom = new MatTableDataSource();
  //arrayTrocasBom : TrocaDisplay[];
  pedidosDeTroca: Troca[];
  pedidosDeTrocaBom: Troca[];
  troca: String[];
  idsDasTrocas: String[];
  displayedColumns = ['Vigilante', 'Exame', 'Dia', 'Acoes'];


  constructor(private docenteService: DocenteService, private exameService: ExameService, private trocaService: TrocasService, private router: Router, private route: ActivatedRoute) { }

  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngAfterViewInit() {
    this.arrayTrocasBom.paginator = this.paginator;
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.fetchPedidosDeTroca();
    });
  }

  fetchPedidosDeTrocaBom() {
    this.arrayTrocasBom.data = this.arrayTrocas;
  }

  fetchPedidosDeTroca() {
    this.arrayTrocas = [];
    this.trocaService.getTrocas().subscribe((data: Troca[]) => {
      for (let index = 0; index < data.length; index++) {
        var idDocente = data[index].vigilanteID;
        var docenteVar: Docente;
        this.docenteService.getDocente(idDocente).subscribe((docente: Docente) => {
          docenteVar = docente;
          var nomeDocente = docenteVar.nome;
          var idExame = data[index].exameID;
          var exameVar: Exame;
          this.exameService.getVigilancia(idExame).subscribe((exame: Exame) => {
            exameVar = exame;
            var nomeExame = exameVar.disciplina;
            var diaExame = exameVar.dataInicio;
            var data = new Date(diaExame);
            var ano = data.getFullYear();
            var mes = data.getMonth();
            var dia = data.getDate();
            var diaInt = +dia;
            var diaCerto;
            if (diaInt < 10) {
              diaCerto = "0" + dia;
            }
            else{
              diaCerto = dia;
            }
            var mete = diaCerto + " - 0" + mes + " - " + ano;
            const troca = {
              nomeDocente: nomeDocente,
              nomeExame: nomeExame,
              diaExame: mete
            }
            this.arrayTrocas.push(troca);
          });
        })
      }
    })
  }


  deleteTroca(id) {
    this.trocaService.deleteTroca(id).subscribe(() => {
      this.fetchPedidosDeTrocaBom();
    });
  }

  fazerATroca(listaIDs) {
    this.idsDasTrocas = listaIDs;

  }
}


