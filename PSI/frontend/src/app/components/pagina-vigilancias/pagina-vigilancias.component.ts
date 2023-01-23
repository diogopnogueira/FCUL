import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { DocenteService } from '../../services/docente.service';
import { Docente } from '../../models/docente.model';


@Component({
  selector: 'app-pagina-vigilancias',
  templateUrl: './pagina-vigilancias.component.html',
  styleUrls: ['./pagina-vigilancias.component.css']
})
export class PaginaVigilanciasComponent implements OnInit {

  displayedColumns: string[] = ['name', 'number'];
  public dataSource = new MatTableDataSource();


  constructor(private docenteService: DocenteService) { }

  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  ngOnInit() {

  }

  getDocentes() {
    this.docenteService.getAllDocentes().subscribe((docentes: Docente[]) => {
      this.dataSource.data = docentes;
    });
    console.log(this.dataSource);
  }


}
