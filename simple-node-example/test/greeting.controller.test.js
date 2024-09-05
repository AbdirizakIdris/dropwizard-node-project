const chai = require('chai');
const sinon = require('sinon');
const proxyquire = require('proxyquire');
const express = require('express');
const supertest = require('supertest');
const { expect } = chai;


const mockGreetingController = {
  returnHelloWorld: sinon.stub().callsFake((req, res) => {
    res.json({ text: 'Mocked Hello World' });
  }),
  postGreeting: sinon.stub().callsFake((req, res) => {
    res.json({ name: req.body.name });
  })
};


const mockPaths = {
  greeting: {
    path: '/greeting',
    action: 'get'
  },
  process_greeting: {
    path: '/process-greeting-node',
    action: 'post'
  }
};

const routes = proxyquire('../src/routes.js', {
  '../src/greeting.controller.js': mockGreetingController,
  '../src/paths.js': mockPaths
});

const app = express();
app.use(express.json());
app.use(routes);

describe('Greeting Controller', () => {
  it('should return 200 and mocked hello world message for GET /greeting', (done) => {
    supertest(app)
      .get('/greeting')
      .expect(200)
      .expect('Content-Type', /json/)
      .expect({ text: 'Mocked Hello World' }, done);
  });

  it('should return 200 and mocked hello world for POST /greeting', (done) => {
    supertest(app)
      .post('/process-greeting-node')
      .send({ name: 'Hello Ali' })
      .expect(200)
      .expect('Content-Type', /json/)
      .expect({ name: 'Hello Ali' }, done);
  });
});