const { Router } = require('express');
const userController = require('../controller/userController');

const router = Router();

router.get('/users/:id', userController.getUserCalorie);
router.post('/users/:id', userController.postUserCalorie);
router.put('/users/:id', userController.updateUserCalorie);

module.exports = router;