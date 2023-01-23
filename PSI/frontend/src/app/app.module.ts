import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FlatpickrModule } from 'angularx-flatpickr';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { CdkTableModule} from '@angular/cdk/table';
import { MatPaginatorModule } from '@angular/material';

import {
  MatToolbarModule, MatFormFieldModule, MatInputModule,
  MatOptionModule, MatSelectModule, MatIconModule,
  MatButtonModule, MatCardModule, MatTableModule,
  MatDividerModule, MatSnackBarModule, MatDialogModule
} from '@angular/material';

import { AppRoutingModule } from './app-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';

import { ExameService } from './services/exame.service';
import { DocenteService } from './services/docente.service';
import { CalendarioService } from './services/calendario.service';

import { AppComponent } from './app.component';
import { ListaVigilanciasComponent } from './components/lista-vigilancias/lista-vigilancias.component';
import { ListaDocentesComponent } from './components/lista-docentes/lista-docentes.component';
import { ListaExamesComponent } from './components/lista-exames/lista-exames.component';
import { CreateCalendarComponent } from './components/create-calendar/create-calendar.component';
import { CheckVigilanceComponent } from './components/check-vigilance/check-vigilance.component';
import { PaginaDocenteComponent } from './components/pagina-docente/pagina-docente.component';
import { PaginaPrincipalComponent } from './components/pagina-principal/pagina-principal.component';
import { PaginaRegenteComponent } from './components/pagina-regente/pagina-regente.component';
import { PaginaTrocasComponent } from './components/pagina-trocas/pagina-trocas.component';
import { PaginaGestorComponent } from './components/pagina-gestor/pagina-gestor.component';
import { CalendarComponent } from './components/calendar/calendar.component';


import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { DxSelectBoxModule, DxListModule, DxTemplateModule } from 'devextreme-angular';
import DataSource from 'devextreme/data/data_source';
import { TrocasService } from './services/trocas.service';

import ArrayStore from 'devextreme/data/array_store';

import { DxTextBoxModule, DxFileUploaderModule, DxButtonModule } from 'devextreme-angular';
import { PaginaVigilanciasComponent } from './components/pagina-vigilancias/pagina-vigilancias.component';
import { PaginaVigilanciasDocenteComponent } from './components/pagina-vigilancias-docente/pagina-vigilancias-docente.component';



const routes: Routes = [
  { path: 'createCalendar', component: CreateCalendarComponent },
  { path: 'checkVigilance', component: CheckVigilanceComponent },
  { path: 'paginaDocente/:id', component: PaginaDocenteComponent },
  { path: 'paginaGestor', component: PaginaGestorComponent },
  { path: 'paginaRegente/:id', component: PaginaRegenteComponent },
  { path: 'paginaPrincipal', component: PaginaPrincipalComponent },
  { path: 'listaVigilancias/:id', component: ListaVigilanciasComponent },
  { path: 'listaDocentes', component: ListaDocentesComponent },
  { path: 'listaExames', component: ListaExamesComponent },
  { path: 'paginaTrocas', component: PaginaTrocasComponent },
  { path: 'calendar', component: CalendarComponent },
  { path: 'paginaVigilancias', component: PaginaVigilanciasComponent },
  { path: 'paginaVigilanciasDocente/:id', component: PaginaVigilanciasDocenteComponent },
  { path: '', redirectTo: 'paginaPrincipal', pathMatch: 'full' },
];


@NgModule({
  declarations: [
    AppComponent,
    ListaVigilanciasComponent,
    ListaDocentesComponent,
    ListaExamesComponent,
    CreateCalendarComponent,
    CheckVigilanceComponent,
    PaginaDocenteComponent,
    PaginaPrincipalComponent,
    PaginaRegenteComponent,
    PaginaTrocasComponent,
    PaginaGestorComponent,
    CalendarComponent,
    PaginaVigilanciasComponent,
    PaginaVigilanciasDocenteComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    RouterModule.forRoot(routes),
    MatToolbarModule,
    MatFormFieldModule,
    MatInputModule,
    MatOptionModule,
    MatSelectModule,
    MatIconModule,
    MatButtonModule,
    MatCardModule,
    MatTableModule,
    MatDividerModule,
    MatSnackBarModule,
    MatDialogModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    BrowserModule,
    DxSelectBoxModule,
    DxListModule,
    DxTemplateModule,
    DxTextBoxModule,
    DxFileUploaderModule,
    DxButtonModule,
    CommonModule,
    FormsModule,
    NgbModalModule,
    BrowserAnimationsModule,
    CdkTableModule,
    MatPaginatorModule,
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory
    })
  ],
  providers: [ExameService, DocenteService, CalendarioService, TrocasService],
  bootstrap: [AppComponent],
})
export class AppModule { }
