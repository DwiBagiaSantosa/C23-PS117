const { Router } = require("express");
const authController = require("../controller/authController");

const router = Router();

router.get("/signup", authController.signupPage);
router.post("/signup", authController.createNewUser);

router.get("/login", authController.loginPage);
router.post("/login", authController.userLogin);

router.get("/reset-password", authController.passwordPage);
router.put("/reset-password", authController.resetPassword);

router.get("/logout", authController.userLogout);

module.exports = router;
