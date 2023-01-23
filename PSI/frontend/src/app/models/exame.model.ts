export interface Exame {
  id: string;
  codigo: number;
  disciplina: string;
  semestre: string;
  epoca: string;
  dataInicio: Date;
  dataFim: Date;
  duracao: string;
  salas: string;
  cursos: string[];
  numeroVigilantes: number;
  vigilantes: string[];
}
