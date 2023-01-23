export interface Docente {
  id: string;
  nome: string;
  departamento: string;
  regente: boolean;
  regencias?: string[];
  sabatica: boolean;
  categoria: string;
  responsavelGestao: boolean;
  numeroVigilancias: number;
  listaVigilancias?: string[];
  unidadesLecionadas: string[];
}
