import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { Docente } from '../../models/docente.model';
import { DocenteService } from '../../services/docente.service';
import { Exame } from '../../models/exame.model';
import { ExameService } from '../../services/exame.service';
import { Router, ActivatedRoute } from '@angular/router';


export interface VigilanciasDataSource {
  nomeDocente: string;
  numeroTotalVigilancias: number;
  numeroVigilanciasSemestre1: number;
  numeroVigilanciasSemestre2: number;
}

@Component({
  selector: 'app-pagina-vigilancias-docente',
  templateUrl: './pagina-vigilancias-docente.component.html',
  styleUrls: ['./pagina-vigilancias-docente.component.css']
})
export class PaginaVigilanciasDocenteComponent implements OnInit {

  displayedColumns: string[] = ['name', 'number', 'semestre1', 'semestre2'];
  dataSource = [];
  id: string;
  numeroVigilancias = 0;
  media = 0;
  nomeDocente = '';
  ano = 'Ano 2018/2019';
  vigilancias: Exame[] = [];
  numeroVigilanciaSemestre1 = 0;
  numeroVigilanciaSemestre2 = 0;
  public dataSourceBom = new MatTableDataSource();
  arrayDeDados = [];
  //dataSourceBom: VigilanciasDataSource[] = [];

  constructor(private docenteService: DocenteService,
    private exameService: ExameService,
    private router: Router,
    private route: ActivatedRoute) { }

  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngAfterViewInit() {
    this.dataSourceBom.paginator = this.paginator;
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = params.id;
      this.getNumeroVigilancias(this.id);
      this.mediaNumeroVigilancias();
      this.getDocentes();
    });
    document.getElementById("1819").style.display = "none";
  }

  getNumeroVigilancias(id) {
    this.docenteService.getDocente(id).subscribe((docente: Docente) => {
      this.numeroVigilancias = docente.numeroVigilancias;
      this.nomeDocente = docente.nome;
    });
  }


  getDocentes() {

    this.docenteService.getAllDocentes().subscribe((docentes: Docente[]) => {
      for (let x = 0; x < docentes.length; x++) {
        //debugger;
        const data = {
          nomeDocente: '',
          numeroTotalVigilancias: 0,
          numeroVigilanciasAno1617Semestre1: 0,
          numeroVigilanciasAno1617Semestre2: 0,
          numeroVigilanciasAno1718Semestre1: 0,
          numeroVigilanciasAno1718Semestre2: 0,
          numeroVigilanciasAno1819Semestre1: 0,
          numeroVigilanciasAno1819Semestre2: 0
        };
        data.nomeDocente = docentes[x].nome;
        data.numeroTotalVigilancias = docentes[x].numeroVigilancias;


        // console.log(nome);
        for (let j = 0; j < docentes[x].listaVigilancias.length; j++) {

          const vigilancia = docentes[x].listaVigilancias[j];
          this.exameService.getVigilancia(vigilancia).subscribe((exame: Exame) => {
            //console.log("entrou");
            const newDate = new Date(exame.dataInicio);
            var anoInt = newDate.getFullYear();
            if (anoInt == 2017) {
              if (exame.semestre === '1º Semestre') {
                data.numeroVigilanciasAno1617Semestre1++;
              } else {
                data.numeroVigilanciasAno1617Semestre2++;
              }
            } else if (anoInt == 2018) {
              if (exame.semestre === '1º Semestre') {
                data.numeroVigilanciasAno1718Semestre1++;
              } else {
                data.numeroVigilanciasAno1718Semestre2++;
              }
            } else if (anoInt == 2019) {
              if (exame.semestre === '1º Semestre') {
                data.numeroVigilanciasAno1819Semestre1++;
              } else {
                data.numeroVigilanciasAno1819Semestre2++;
              }
            }
          });
        }
        this.dataSource.push(data);
      }
    });
  }

  changeView(ano) {
    this.arrayDeDados = [];
    switch (ano) {
      case 'Ano 2016/2017':
        document.getElementById("1617").style.display = "none";
        document.getElementById("1819").style.display = "block";
        document.getElementById("1718").style.display = "block";
        this.dataSource.forEach(element => {
          const cenas = {
            nomeDocente: element.nomeDocente,
            numeroTotalVigilancias: element.numeroTotalVigilancias,
            numeroVigilanciasSemestre1: element.numeroVigilanciasAno1617Semestre1,
            numeroVigilanciasSemestre2: element.numeroVigilanciasAno1617Semestre2
          };
          this.arrayDeDados.push(cenas);
        });
        break;
      case 'Ano 2017/2018':
        document.getElementById("1718").style.display = "none";
        document.getElementById("1819").style.display = "block";
        document.getElementById("1617").style.display = "block";
        this.dataSource.forEach(element => {
          const cenas = {
            nomeDocente: element.nomeDocente,
            numeroTotalVigilancias: element.numeroTotalVigilancias,
            numeroVigilanciasSemestre1: element.numeroVigilanciasAno1718Semestre1,
            numeroVigilanciasSemestre2: element.numeroVigilanciasAno1718Semestre2
          };
          this.arrayDeDados.push(cenas);
        });
        break;
      case 'Ano 2018/2019':
        document.getElementById("1819").style.display = "none";
        document.getElementById("1718").style.display = "block";
        document.getElementById("1617").style.display = "block";
        this.dataSource.forEach(element => {
          const cenas: VigilanciasDataSource = {
            nomeDocente: element.nomeDocente,
            numeroTotalVigilancias: element.numeroTotalVigilancias,
            numeroVigilanciasSemestre1: element.numeroVigilanciasAno1819Semestre1,
            numeroVigilanciasSemestre2: element.numeroVigilanciasAno1819Semestre2
          };
          this.arrayDeDados.push(cenas);
          console.log(this.arrayDeDados);
        });
        break;
    }
  }

  changeViewBotoes(ano) {
    document.getElementById("mostra").style.display = "block";
    document.getElementById("tabela").style.display = "none";
    if (ano != this.ano) {
      this.ano = ano;
      this.changeView(ano);
    }
  }


  fetchVigilanciasBoas() {
    document.getElementById("tabela").style.display = "block";
    document.getElementById("mostra").style.display = "none";
    this.changeView(this.ano);
    console.log(this.dataSource);
    this.dataSourceBom.data = this.arrayDeDados;

  }


  mediaNumeroVigilancias() {
    this.docenteService.buscaDocentes().subscribe((docentes: Docente[]) => {
      let soma = 0;
      for (let i = 0; i < docentes.length; i++) {
        soma += docentes[i].numeroVigilancias;
      }
      this.media = Math.round(soma / docentes.length);
    });
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
