import mongoose from 'mongoose';
import cors from 'cors';

const Schema = mongoose.Schema;

let Docente = new Schema({
    nome: {
        type: String
    },
    departamento: {
        type: String
    },
    regente: {
        type: Boolean
    },
    regencias: {
        type: [String]
    },
    sabatica: {
        type: Boolean
    },
    categoria: {
        type: String
    },
    responsavelGestao: {
        type: Boolean
    },
    numeroVigilancias: {
        type: Number
    },
    listaVigilancias: {
        type: [String]
    },
    unidadesLecionadas:{
        type: [String]
    }

}, {autoCreate: true});

export default mongoose.model('Docente', Docente);