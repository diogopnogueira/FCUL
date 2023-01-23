import mongoose from 'mongoose';
import Exame from './Exame'
import cors from 'cors';

const Schema = mongoose.Schema;

let Troca = new Schema({
    vigilanteID: {
        type: String
    },
    exameID: {
        type: String
    }
});

export default mongoose.model('Troca', Troca);