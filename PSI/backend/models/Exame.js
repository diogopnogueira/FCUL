import mongoose from 'mongoose';
import cors from 'cors';

const Schema = mongoose.Schema;

let Exame = new Schema({
    codigo: {
        type: Number
    },
    disciplina: {
        type: String
    },
    semestre: {
        type: String
    },
    epoca: {
        type: String
    },
    dataInicio: {
        type: Date
    },
    dataFim: {
        type: Date
    },
    duracao: {
        type: String
    },
    salas: {
        type: String
    },
    cursos: {
        type: [String]
    },
    numeroVigilantes:{
        type: Number
    },
    vigilantes:{
        type: [String],
        default: []
    }
}, {autoCreate: true});

export default mongoose.model('Exame', Exame);