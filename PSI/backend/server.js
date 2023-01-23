import express from 'express';
import cors from 'cors';
import bodyParser from 'body-parser';
import mongoose from 'mongoose';

import Docente from './models/Docente'
import Exame from './models/Exame'
import Troca from './models/Troca'

import { runInNewContext } from 'vm';

const app = express();
const router = express.Router();

app.use(cors());
app.use(bodyParser.json());

mongoose.connect("mongodb://psi018:psi018@localhost:27017/psi018?retryWrites=true&authSource=psi018");

mongoose.connection.on('connected', () => console.log('Connected to server!'));
mongoose.connection.on('error', () => console.log('Connection failed with error!'));


router.route('/exames').get((req, res) => {
  Exame.find((err, exames) => {
    if (err)
      console.log(err);
    else
      res.json(exames);
  });
});


router.route('/exames/numeroVigs/:id').get((req, res) => {
  Exame.aggregate([
    {
      $match: {
        vigilantes: { $in: req.params.id }
      }
    }, { $count: "numeroVigs" }], (err, exames) => {
      if (err)
        console.log(err);
      else {
        res.json(exames);
      }
    });
});

router.route('/exames/vigilanciasbydocente1s/:id').get((req, res) => {
  let sem = "1º Semestre";
  Exame.find({ vigilantes: { $in: req.params.id }, semestre: { $eq: sem } }, (err, exames) => {
    if (err)
      console.log(err);
    else {
      res.json(exames);
    }
  });
});

router.route('/exames/vigilanciasbydocente2s/:id').get((req, res) => {
  let sem = "2º Semestre";
  Exame.find({ vigilantes: { $in: req.params.id }, semestre: { $eq: sem } }, (err, exames) => {
    if (err)
      console.log(err);
    else {
      res.json(exames);
    }
  });
});


router.route('/exames/:id').get((req, res) => {
  Exame.findById(req.params.id, (err, exame) => {
    if (err)
      console.log(err);
    else
      res.json(exame);
  });
});

router.route('/exames/add').post((req, res) => {
  let exame = new Exame(req.body);
  exame.save()
    .then(exame => {
      res.status(200).json({ 'exame': 'Added successfully!' })
    })
    .catch(err => {
      res.status(400).send('Failed to create new record!');
    })
});

router.route('/exames/update/:id').post((req, res) => {
  Exame.findById(req.params.id, (err, exame) => {
    if (!exame) {
      return next(new Error('Could not load document'));
    }
    else {
      exame.codigo = req.body.codigo;
      exame.disciplina = req.body.disciplina;
      exame.semestre = req.body.semestre;
      exame.epoca = req.body.epoca;
      exame.dataInicio = req.body.dataInicio;
      exame.dataFim = req.body.dataFim;
      exame.duracao = req.body.duracao;
      exame.salas = req.body.salas;
      exame.cursos = req.body.cursos;
      exame.numeroVigilantes = req.body.numeroVigilantes;
      exame.vigilantes = req.body.vigilantes;

      exame.save().then(exame => {
        res.json('Update done');
      }).catch(err => {
        res.status(400).send('Update failed');
      });
    }
  });
});



router.route('/exames/atribui/:id').post((req, res) => {
  for (var i = 0; i < req.body.number; i++) {
    let id;
    Docente.aggregate([{
      $match: {
        categoria: { $nin: ['catedratico', 'associado'] }, sabatica: { $eq: false },
        responsavelGestao: { $eq: false }, listaVigilancias: { $nin: [req.params.id] }
      }
    },
    { $sort: { numeroVigilancias: 1 } }, { $limit: 1 }], (err, docentes) => {
      if (err)
        console.log(err);
      else {
        let docente = docentes[0];
        id = new String(docente._id);
        Exame.findById(req.params.id, (err, exame) => {
          if (!exame) {
            return next(new Error('Could not load document'));
          }
          else {
            exame.vigilantes.push(id);
            exame.save().catch(err => {
              res.status(350).send('Update failed');
            });

          }
        });
      }
      Docente.findById(id, (err, docente) => {
        if (!docente) {
          return next(new Error('Could not load document'));
        }
        else {
          docente.numeroVigilancias = docente.numeroVigilancias + 1;
          docente.listaVigilancias.push(req.params.id);
          docente.save().catch(err => {
            res.status(400).send('Update failed');
          });
        }
      });
    });
  }
  res.json(".");
});

router.route('/docentes/metevig/:id').post((req, res) => {
  Docente.findById(req.params.id, (err, docente) => {
    if (!docente) {
      return next(new Error('Could not load document'));
    }
    else {
      docente.numeroVigilancias = req.body.num;
      docente.listaVigilancias = req.body.vig;
      docente.save().then(issue => {
        res.json('Update done');
      }).catch(err => {
        res.status(400).send('Update failed');
      });
    }
  });
});


router.route('/trocas').get((req, res) => {
  Troca.find((err, trocas) => {
    if (err)
      console.log(err);
    else
      res.json(trocas);
  });
});

router.route('/trocas/add').post((req, res) => {
  let troca = new Troca(req.body);
  troca.save()
    .then(troca => {
      res.status(200).json({ 'troca': 'Added successfully!' })
    })
    .catch(err => {
      res.status(400).send('Failed to create new record!');
    })
})

router.route('/trocas/delete/:id').get((req, res) => {
  Troca.findByIdAndRemove({ _id: req.params.id }, (err, troca) => {
    if (err)
      res.json(err);
    else
      res.json('Remove Successfully')
  })
})

router.route('/docentes').get((req, res) => {
  Docente.find((err, docentes) => {
    if (err)
      console.log(err);
    else
      res.json(docentes);
  });
});

router.route('/docentes/getLeastVigilancia').get((req, res) => {
  Docente.aggregate([{
    $match: {
      categoria: { $ne: "catedratico" }, sabatica: { $eq: false },
      categoria: { $ne: "associado" }, responsavelGestao: { $eq: false }
    }
  },
  { $sort: { numeroVigilancias: 1 } }, { $limit: 1 }], (err, docentes) => {
    //listaVigilancias: { $nin: [req.params.id]}}, (err, docentes) => { 
    if (err)
      console.log(err);
    else {
      res.json(docentes);
    }
  });
});

router.route('/docentes/add/:id').post((req, res) => {
  Docente.findById(req.params.id, (err, docente) => {
    if (!docente) {
      docente = new Docente(req.body);
      docente.save()
        .then(docente => {
          res.status(200).json({ 'docente': 'Added successfully!' })
        })
        .catch(err => {
          res.status(400).send('Failed to create new docente!');
        });
    }
    else {
      docente.nome = req.body.nome;
      docente.departamento = req.body.departamento;
      docente.regente = req.body.regente;
      docente.regencias = req.body.regencias;
      docente.sabatica = req.body.sabatica;
      docente.categoria = req.body.categoria;
      docente.responsavelGestao = req.body.responsavelGestao;
      docente.numeroVigilancias = req.body.numeroVigilancias;
      docente.listaVigilancias = req.body.listaVigilancias;
      docente.unidadesLecionadas = req.body.unidadesLecionadas;

      docente.save().then(issue => {
        res.json('Update done');
      }).catch(err => {
        res.status(400).send('Update failed');
      });
    }
  });
});

router.route('/docentes/sreg').get((req, res) => {
  Docente.find({ regente: { $eq: false } }, (err, docente) => {
    if (err)
      console.log(err);
    else
      res.json(docente);
  });
});


router.route('/docentes/regente').get((req, res) => {
  Docente.find({ regente: { $eq: true } }, (err, docente) => {
    if (err)
      console.log(err);
    else
      res.json(docente);
  });
});

router.route('/docentes/:id').get((req, res) => {
  Docente.findById(req.params.id, (err, docente) => {
    if (err)
      console.log(err);
    else
      res.json(docente);
  });
});

router.route('/docentes/update/:id').post((req, res) => {
  Docente.findById(req.params.id, (err, docente) => {
    if (!docente) {
      return next(new Error('Could not load document'));
    }
    else {
      docente.nome = req.body.nome;
      docente.departamento = req.body.departamento;
      docente.regente = req.body.regente;
      docente.regencias = req.body.regencias;
      docente.sabatica = req.body.sabatica;
      docente.categoria = req.body.categoria;
      docente.responsavelGestao = req.body.responsavelGestao;
      docente.numeroVigilancias = req.body.numeroVigilancias;
      docente.listaVigilancias = req.body.listaVigilancias;
      docente.unidadesLecionadas = req.body.unidadesLecionadas;

      docente.save().then(issue => {
        res.json('Update done');
      }).catch(err => {
        res.status(400).send('Update failed');
      });
    }
  });
});



app.use('/', router);

app.listen(3048, () => console.log('express server running on port 3048'));

