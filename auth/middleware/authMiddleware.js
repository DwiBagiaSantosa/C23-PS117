const jwt = require('jsonwebtoken');

const verifyToken = (req, res, next) => {
  const token = req.cookies.access_token;

  // check jsonwebtoken exist
  if (!token) {
    res.redirect('/login');
  }

  jwt.verify(token, process.env.ACCESS_TOKEN_SECRET, (err, decoded) => {
    if(err) {
      console.log(err.message);
      res.redirect('/login');
    }

    console.log(decoded);
    next();
  });
}

module.exports = { verifyToken }