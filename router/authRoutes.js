const { Router } = require('express');
const authController = require('../controller/authController');

const router = Router();

router.get('/signup', authController.signupPage);
router.get('/login', authController.loginPage);
router.post('/signup', authController.createNewUser);
router.post('/login', authController.userLogin);
router.get('/logout', authController.userLogout);

module.exports = router;