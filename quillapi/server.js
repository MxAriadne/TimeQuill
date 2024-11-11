const express = require('express');
const cors = require('cors')
const sqlite3 = require('sqlite3').verbose();
const app = express();
const port = 3000;

// Connect to SQLite database
const db = new sqlite3.Database('./database.db');

app.use(cors())

// Example endpoint to get data
app.get('/api/projects', (req, res) => {
  db.all('SELECT * FROM projects', [], (err, rows) => {
    if (err) {
      res.status(500).send(err.message);
    } else {
      res.json(rows);
    }
  });
});

app.get('/', (req, res) => {
  res.json({ message: "And we\"re rolling!" });
});

app.listen(port, () => {
  console.log(`Server running at http://localhost:${port}`);
});
