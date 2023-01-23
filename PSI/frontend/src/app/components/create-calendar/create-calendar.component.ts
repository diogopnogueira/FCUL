import { Component, OnInit } from '@angular/core';
import { MatDialog, MatSnackBar } from '@angular/material';
import { Router } from '@angular/router';

import { DocenteService } from 'src/app/services/docente.service';
import { CalendarioService } from 'src/app/services/calendario.service';
import { ExameService } from 'src/app/services/exame.service';

import { Exame } from '../../models/exame.model';
import { Docente } from 'src/app/models/docente.model';
import * as XLSX from 'xlsx';
import { DebugRenderer2 } from '@angular/core/src/view/services';

export interface Ano {
  value: string;
  viewValue: string;
}
export interface Semestre {
  value: string;
  viewValue: string;
}


@Component({
  selector: 'app-create-calendar',
  templateUrl: './create-calendar.component.html',
  styleUrls: ['./create-calendar.component.css'],
})
export class CreateCalendarComponent implements OnInit {

  selectedSemestre = "none";
  selectedAno = "none";

  /*  anos = [
     { value: '1819', viewValue: '2018/2019' },
     { value: '1718', viewValue: '2017/2018' },
     { value: '1617', viewValue: '2016/2017' }
   ];

   semestres = [
     { value: '1', viewValue: '1º Semestre' },
     { value: '2', viewValue: '2º Semestre' },
   ]; */

  exames: Exame[];

  openForm = false;

  ficheiroDocentes = [];
  ficheiroCategorias = [];
  ficheiroExames = [];
  ficheiroSalas = [];
  examesDoDI = [];
  examesDoDIUniqueArray = [];

  listaExames: Exame[];
  listaDocentes: Docente[];
  listaExamesDI: Exame[];

  listaDocentesVindosDaDb: Docente[];
  listaExamesVindosDaDb: Exame[];

  fileDocentesUpload: FileList;
  fileCategoriasUpload: FileList;
  fileExamesUpload: FileList;
  fileSalasUpload: FileList;

  nomeFicheiro1: string = 'Upload';
  nomeFicheiro2: string = 'Upload';
  nomeFicheiro3: string = 'Upload';
  nomeFicheiro4: string = 'Upload';


  constructor(private calendarioService: CalendarioService,
    private docenteService: DocenteService,
    private exameService: ExameService,
    private dialog: MatDialog,
    private router: Router,
    private snackBar: MatSnackBar) { }

  ngOnInit() {
    document.getElementById("popup1").style.display = "none";
    document.getElementById("popup2").style.display = "none";

    this.docenteService.getAllDocentes().subscribe((data1: Docente[]) => {
      this.listaDocentes = data1;
    });

    this.exameService.getExames().subscribe((data2: Exame[]) => {
      this.listaExames = data2;
    });
  }

  getFiles(desig, event) {
    switch (desig) {
      case 'docentes':
        this.fileDocentesUpload = event.target.files;
        this.nomeFicheiro1 = event.target.files[0].name;
        this.excelToJson(desig, this.fileDocentesUpload[0]);
        break;
      case 'categorias':
        this.fileCategoriasUpload = event.target.files;
        this.nomeFicheiro2 = event.target.files[0].name;
        this.excelToJson(desig, this.fileCategoriasUpload[0]);
        break;
      case 'exames':
        this.fileExamesUpload = event.target.files;
        this.nomeFicheiro3 = event.target.files[0].name;
        this.excelToJson(desig, this.fileExamesUpload[0]);
        break;
      case 'salas':
        this.fileSalasUpload = event.target.files;
        this.nomeFicheiro4 = event.target.files[0].name;
        this.excelToJson(desig, this.fileSalasUpload[0]);
        break;
      default:
        alert('Erro excelToJson');
    }
    if (this.nomeFicheiro1 !== 'Upload' && this.nomeFicheiro2 !== 'Upload' &&
      this.nomeFicheiro3 !== 'Upload' && this.nomeFicheiro4 !== 'Upload' &&
      this.selectedAno !== 'none' && this.selectedSemestre !== 'none') {
      console.log("entrou if");
      document.getElementById("botaoGerar").removeAttribute("disabled");
    }
  }

  excelToJson(desig, file) {
    const reader = new FileReader();
    const self = this;
    reader.onload = function (e: any) {
      const data = new Uint8Array(e.target.result);
      const workbook = XLSX.read(data, { type: 'array' });
      const firstSheetName = workbook.SheetNames[0];
      const secondSheetName = workbook.SheetNames[1];
      const worksheet1 = workbook.Sheets[firstSheetName];
      const worksheet2 = workbook.Sheets[secondSheetName];

      switch (desig) {
        case 'docentes':
          self.ficheiroDocentes = XLSX.utils.sheet_to_json(worksheet1, { raw: true });
          self.ficheiroDocentes = self.ficheiroDocentes.concat(XLSX.utils.sheet_to_json(worksheet2, { raw: true }));
          console.log(self.ficheiroDocentes);
          break;
        case 'categorias':
          self.ficheiroCategorias = XLSX.utils.sheet_to_json(worksheet1, { raw: true });
          console.log(self.ficheiroCategorias);
          break;
        case 'exames':
          self.ficheiroExames = XLSX.utils.sheet_to_json(worksheet1, { raw: true });
          console.log(self.ficheiroExames);
          break;
        case 'salas':
          self.ficheiroSalas = XLSX.utils.sheet_to_json(worksheet1, { raw: true });
          console.log(self.ficheiroSalas);
          break;
        default:
          alert('Erro excelToJson');
      }
    }
    reader.readAsArrayBuffer(file);
  }

  createCalendar() {
    document.getElementById("voltar").style.display = "none";
    console.log(this.listaDocentes);

    console.log(this.listaExames);

    document.getElementById("popup1").style.display = "block";


    this.adicionarDocentes();
    this.adicionarExames();


    var self = this;
    setTimeout((e) => {
      self.docenteService.getAllDocentes().subscribe((data1: Docente[]) => {
        self.listaDocentesVindosDaDb = data1;

        self.exameService.getExames().subscribe((data2: Exame[]) => {
          self.listaExamesVindosDaDb = data2;

          self.atribuirExames();

            self.listaDocentesVindosDaDb.forEach(element => {
            var infoDoDocente = Object.values(element);
            self.docenteService.updateDocente(infoDoDocente[3], element.nome, element.departamento, element.regente, element.regencias,
              element.sabatica, element.categoria, element.responsavelGestao, element.numeroVigilancias,
              element.listaVigilancias, element.unidadesLecionadas);
          });
          self.listaExamesVindosDaDb.forEach(element => {
            var infoDoExame = Object.values(element);
            self.exameService.updateExame(infoDoExame[2], element.codigo, element.disciplina, element.semestre,
              element.epoca, element.dataInicio, element.dataFim, element.duracao, element.salas,
              element.cursos, element.numeroVigilantes, element.vigilantes);
          });

          self.listaExamesVindosDaDb.forEach(element => {
            var infoDoExame = Object.values(element);
            self.exameService.atribuiExames(infoDoExame[2],(element.numeroVigilantes - element.vigilantes.length));
           });

          document.getElementById("popup1").style.display = "none";
          document.getElementById("popup2").style.display = "block";
          this.snackBar.open("Calendário Gerado", "Ok", {
            duration: 4000,
          });
          setTimeout(function () {
            self.router.navigate(['/paginaGestor']);
          }, 2000);
        });
      });
    }, 10000);
  }



  private atribuirExames() {
    this.listaExamesVindosDaDb.forEach(exame => {
      var infoDoExame = Object.values(exame);
      this.listaDocentesVindosDaDb.forEach(docente => {
        if (!docente.sabatica && !docente.responsavelGestao && docente.categoria != "associado" &&
          docente.categoria != "catedratico") {
          var infoDoDocente = Object.values(docente);
          docente.unidadesLecionadas.forEach(cadeira => {
            if (cadeira === exame.disciplina && exame.numeroVigilantes > exame.vigilantes.length) {
              if (this.checkDisponibilidade(exame, docente.listaVigilancias)) {
                docente.listaVigilancias.push(infoDoExame[2]);
                exame.vigilantes.push(infoDoDocente[3]);
                docente.numeroVigilancias += 1;
              }
            }
          });
        }
      });
    });

  }


  private checkDisponibilidade(exame: Exame, lista: string[]) {
    var infoDoExame = Object.values(exame);
    lista.forEach(id => {
      var vigilancia: Exame;
      this.exameService.
        getVigilancia(id).subscribe((data: Exame) => {
          vigilancia = data;
          if (id === infoDoExame[2]) {
            return false;
          }
          if (this.compareDate(exame.dataInicio, vigilancia.dataInicio) < 1 &&
            this.compareDate(exame.dataFim, vigilancia.dataInicio) > 0 ||
            this.compareDate(exame.dataInicio, vigilancia.dataFim) < 0 &&
            this.compareDate(exame.dataFim, vigilancia.dataFim) > -1) {
            return false;
          }
        });
    });
    return true;
  }

  private compareDate(date1: Date, date2: Date): number {
    let d1 = new Date(date1); let d2 = new Date(date2);
    // Check if the dates are equal
    let same = d1.getTime() === d2.getTime();
    if (same) return 0;
    // Check if the first is greater than second
    if (d1 > d2) return 1;
    // Check if the first is less than second
    if (d1 < d2) return -1;
  }

  private adicionarExames() {

    this.ficheiroDocentes.forEach(linhaDocente => {
      this.examesDoDI.push(linhaDocente['UNIDADE CURRICULAR']);
    });

    this.examesDoDIUniqueArray = makeUnique(this.examesDoDI);

    function makeUnique(arr) {
      var uniqueArray = [];
      arr.forEach(function (element) {
        if (element == undefined) {
        }
        else {
          if (uniqueArray.indexOf(element) === -1) {
            uniqueArray.push(element);
          }
        }
      })
      return uniqueArray;
    }

    this.ficheiroExames.forEach(element => {


      //FAZER PARSE DO DIA
      var lookupDiaExame = 'Data ';
      const diaExame = XLSX.SSF.parse_date_code(element[lookupDiaExame], { date1904: false });
      const diaEmJSONString = JSON.stringify(diaExame);
      const ano = diaEmJSONString.substring(diaEmJSONString.indexOf('y') + 3, diaEmJSONString.indexOf('y') + 7);
      const mes = diaEmJSONString.substring(diaEmJSONString.indexOf('m') + 3, diaEmJSONString.indexOf('d') - 2);
      const dia = diaEmJSONString.substring(diaEmJSONString.indexOf('d') + 3, diaEmJSONString.indexOf('H') - 2);
      const dataString = ano + "-" + dia + "-" + mes;



      //ISTO EH A DATA EM FORMATO MM-DD-YYYY!
      const dataEmData = new Date(dataString);
      element[lookupDiaExame] = dataEmData;

      //FAZER PARSE DAS HORAS
      var horaInicio = fazerParseDaHora('Hora Inicio');
      var horaFim = fazerParseDaHora('Hora Fim');
      var duracao = fazerParseDaHora('Duração');
      const duracaoString = duracao[0] + ":" + duracao[1] + ":" + duracao[2];
      element['Duração'] = duracaoString;



      const dataInicio = new Date();
      dataInicio.setFullYear(+ano);
      dataInicio.setMonth(+mes);
      dataInicio.setDate(+dia);
      dataInicio.setHours(horaInicio[0]);
      dataInicio.setMinutes(horaInicio[1]);
      dataInicio.setSeconds(horaInicio[2]);

      const dataFim = new Date();
      dataFim.setFullYear(+ano);
      dataFim.setMonth(+mes);
      dataFim.setDate(+dia);
      dataFim.setHours(horaFim[0]);
      dataFim.setMinutes(horaFim[1]);
      dataFim.setSeconds(horaFim[2]);



      //PARSE DAS HORAS E DURACAO
      function fazerParseDaHora(oQueDarLookup) {
        var lookupHoraInicioExame = oQueDarLookup;
        const horaInicioExame = element[lookupHoraInicioExame];
        var horaInicioExameEmInt = +horaInicioExame; // horaInicioExameEmInt: number

        const horaReal = horaInicioExameEmInt * 24;


        var result = (horaReal - Math.floor(horaReal)) !== 0;
        const horaRealEmString = horaReal + "";
        var horaEmFormatoFinal;
        var minutosEmFormatoFinal;
        var segundosEmFormatoFinal;
        if (result) {

          horaEmFormatoFinal = horaRealEmString.substring(0, 2);

          minutosEmFormatoFinal = "30";
          segundosEmFormatoFinal = "00";
        }
        else {

          horaEmFormatoFinal = horaRealEmString.substring(0, 2);

          minutosEmFormatoFinal = "00";
          segundosEmFormatoFinal = "00";
        }
        const horaFinalEmFormatoHora = [horaEmFormatoFinal, minutosEmFormatoFinal, segundosEmFormatoFinal];

        return horaFinalEmFormatoHora;
      }


      var numeroNecessarioVigilantes = 1;
      if (element['Semestre'] === this.selectedSemestre && ano == this.selectedAno.substring(5)) {
        var salas = element['Sala(s)'].split(" | ");
        for (let index = 0; index < salas.length; index++) {
          var bool = false;
          this.ficheiroSalas.forEach(linha => {
            if (salas[index] === linha['nome']) {
              numeroNecessarioVigilantes = numeroNecessarioVigilantes + linha['numero vigilantes'];
              bool = true;
            };
          });
          if (!bool) {
            numeroNecessarioVigilantes = numeroNecessarioVigilantes + 1;
          }
        };
        var exame: Exame;
        exame = {
          id: "null",
          codigo: element['Código'],
          disciplina: element['Disciplina'],
          semestre: element['Semestre'],
          epoca: element['Época'],
          dataInicio: dataInicio,
          dataFim: dataFim,
          duracao: element['Duração'],
          salas: element['Sala(s)'],
          cursos: element['Cursos (* - opcional)'],
          numeroVigilantes: numeroNecessarioVigilantes,
          vigilantes: []
        };
        this.listaExames.push(exame);
      }
    });

    this.listaExamesDI = [];
    this.listaExames.forEach(exame => {
      for (let index = 0; index < this.examesDoDIUniqueArray.length; index++) {
        if (exame.disciplina === this.examesDoDIUniqueArray[index]) {
          let bool = true;
          this.listaExamesDI.forEach(element => {
            if (element.codigo == exame.codigo && exame.epoca === element.epoca) {
              bool = false;
            }
          });
          if (bool) {
            this.listaExamesDI.push(exame);
          }
        }
      }
    });

    this.listaExamesDI.forEach(element => {
      if (element.id === "null") {
        this.exameService.newExame(element.codigo, element.disciplina, element.semestre, element.epoca,
          element.dataInicio, element.dataFim, element.duracao, element.salas,
          element.cursos, element.numeroVigilantes);
      }
      else {
        var infoDoExame = Object.values(element);
        this.exameService.updateExame(infoDoExame[2], element.codigo, element.disciplina, element.semestre,
          element.epoca, element.dataInicio, element.dataFim, element.duracao, element.salas,
          element.cursos, element.numeroVigilantes, []);
      }
    });
  }



  private adicionarDocentes() {
    const lookupNome = 'Validação de serviço docente 2018/19 na linha correspondente ao turno';
    const lookupDep = 'DEPARTAMENTO';
    const lookupReg = 'Validação da regência 2018/19 ';
    const lookupUnCur = 'UNIDADE CURRICULAR';

    const lookupNomeCat = 'nome';
    const lookupCat = 'categoria ';
    const lookupResG = 'responsavel de gestao';
    const lookupSab = 'sabatica';
    this.ficheiroDocentes.forEach(element => {
      let bool = true;
      this.listaDocentes.forEach(docente => {
        if (docente.nome === element[lookupNome]) {
          let bool2 = true;
          let bool3 = true;
          bool = false;
          docente.unidadesLecionadas.forEach(cadeira => {
            if (element[lookupReg] == element[lookupNome]) {
              docente.regencias.forEach(regenciaC => {
                if (regenciaC === element[lookupUnCur]) {
                  bool3 = false;
                }
              });
              if (bool3) {
                docente.regente = true;
                docente.regencias.push(element[lookupUnCur]);
              }
              bool3 = true;
            }

            if (element[lookupUnCur] === cadeira) {
              bool2 = false;
            }
          });
          if (bool2) {
            docente.unidadesLecionadas.push(element[lookupUnCur]);
          }
        }
      });
      if (bool) {
        var cat;
        var sab = false;
        var res = false;
        var unCur: string[];
        this.ficheiroCategorias.forEach(linha => {
          bool = false;
          var regs = [];
          var reg = false;
          unCur = [];
          if (linha[lookupNomeCat] === element[lookupNome]) {
            bool = true;
            cat = linha[lookupCat];
            if (linha[lookupResG] === "sim") {
              res = true;
            }
            if (linha[lookupSab] === "sim") {
              sab = true;
            }
          }
          if (bool) {
            if (element[lookupReg] == element[lookupNome]) {
              regs.push(element[lookupUnCur]);
              reg = true;
            }
            var docente: Docente;
            unCur.push(element[lookupUnCur]);
            docente = {
              id: "null", nome: element[lookupNome], departamento: element[lookupDep], regente: reg,
              regencias: regs, sabatica: sab, categoria: cat, responsavelGestao: res,
              numeroVigilancias: 0, unidadesLecionadas: unCur
            };
            this.listaDocentes.push(docente);
          }
        });
      }
    });


    this.listaDocentes.forEach(element => {
      if (element.id === "null") {
        this.docenteService.newDocente(element.id, element.nome, element.departamento, element.regente, element.regencias,
          element.sabatica, element.categoria, element.responsavelGestao, element.unidadesLecionadas);
      }
      else {
        var infoDoDocente = Object.values(element);
        this.docenteService.newDocente(infoDoDocente[3], element.nome, element.departamento, element.regente, element.regencias,
          element.sabatica, element.categoria, element.responsavelGestao, element.unidadesLecionadas);
      }
    });

  }
}
