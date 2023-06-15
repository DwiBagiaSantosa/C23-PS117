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

/**
 * @swagger
 * tags:
 *   name: Auth
 *   description: Authentication endpoints
 */

/**
 * @swagger
 * /signup:
 *   get:
 *     summary: Signup page
 *     tags: [Auth]
 *     responses:
 *       200:
 *         description: Signup page
 *   post:
 *     summary: Create a new user
 *     tags: [Auth]
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               name:
 *                 type: string
 *               email:
 *                 type: string
 *               password:
 *                 type: string
 *               confPassword:
 *                 type: string
 *               gender:
 *                 type: string
 *               age:
 *                 type: number
 *               tall:
 *                 type: number
 *               weight:
 *                 type: number
 *             example:
 *               name: John Doe
 *               email: johndoe@example.com
 *               password: password123
 *               confPassword: password123
 *               gender: Male
 *               age: 30
 *               tall: 180
 *               weight: 75
 *     responses:
 *       201:
 *         description: Create new user success
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 message:
 *                   type: string
 *                 data:
 *                   type: object
 *                   properties:
 *                     email:
 *                       type: string
 *                     hashPassword:
 *                       type: string
 *       400:
 *         description: Bad request
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 message:
 *                   type: string
 *       500:
 *         description: Server error
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 message:
 *                   type: string
 *                 serverMessage:
 *                   type: string
 */

/**
 * @swagger
 * /login:
 *   get:
 *     summary: Login Page
 *     tags: [Auth]
 *     responses:
 *       200:
 *         description: Returns the login page
 *   post:
 *     summary: User login
 *     tags: [Auth]
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             properties:
 *               email:
 *                 type: string
 *               password:
 *                 type: string
 *             example:
 *               email: johndoe@example.com
 *               password: password123
 *     responses:
 *       200:
 *         description: User logged in successfully
 *       400:
 *         description: Incorrect password
 *       500:
 *         description: Internal server error
 */

/**
 * @swagger
 * /reset-password:
 *   get:
 *     summary: Password Reset Page
 *     tags: [Auth]
 *     responses:
 *       200:
 *         description: Returns the password reset page
 *   put:
 *     summary: Reset user's password
 *     tags: [Auth]
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             properties:
 *               email:
 *                 type: string
 *             example:
 *               email: johndoe@example.com
 *     responses:
 *       201:
 *         description: Password reset email sent
 *       400:
 *         description: User with the specified email does not exist
 *       500:
 *         description: Internal server error
 */

/**
 * @swagger
 * /logout:
 *   get:
 *     summary: User Logout
 *     tags: [Auth]
 *     responses:
 *       200:
 *         description: User logged out successfully
 */
