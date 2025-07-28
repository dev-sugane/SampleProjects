// ゲームキャンバスの設定
const canvas = document.getElementById('gameCanvas');
const ctx = canvas.getContext('2d');

// ゲーム状態
let gameRunning = false;
let score = 0;
let lives = 3;

// パドルの設定
const paddle = {
    width: 100,
    height: 20,
    x: canvas.width / 2 - 50,
    y: canvas.height - 40,
    speed: 8,
    dx: 0
};

// ボールの設定
const ball = {
    x: canvas.width / 2,
    y: canvas.height - 60,
    radius: 8,
    dx: 4,
    dy: -4
};

// ブロックの設定
const blockRowCount = 5;
const blockColumnCount = 10;
const blockWidth = 75;
const blockHeight = 20;
const blockPadding = 10;
const blockOffsetTop = 60;
const blockOffsetLeft = 30;

// シンプルな色の設定
const colors = {
    paddle: '#333333',
    ball: '#ffffff',
    blocks: ['#cccccc', '#999999', '#666666', '#333333', '#000000']
};

// ブロック配列の初期化
const blocks = [];
for (let c = 0; c < blockColumnCount; c++) {
    blocks[c] = [];
    for (let r = 0; r < blockRowCount; r++) {
        blocks[c][r] = { x: 0, y: 0, status: 1 };
    }
}

// キー入力の管理
const keys = {};
document.addEventListener('keydown', (e) => {
    keys[e.key] = true;
    if (e.key === ' ' && !gameRunning) {
        startGame();
    }
});

document.addEventListener('keyup', (e) => {
    keys[e.key] = false;
});

// ゲーム開始
function startGame() {
    gameRunning = true;
    resetBall();
}

// ボールのリセット
function resetBall() {
    ball.x = canvas.width / 2;
    ball.y = canvas.height - 60;
    ball.dx = 4;
    ball.dy = -4;
}

// 衝突検出
function collisionDetection() {
    for (let c = 0; c < blockColumnCount; c++) {
        for (let r = 0; r < blockRowCount; r++) {
            const b = blocks[c][r];
            if (b.status === 1) {
                if (ball.x > b.x && ball.x < b.x + blockWidth && ball.y > b.y && ball.y < b.y + blockHeight) {
                    ball.dy = -ball.dy;
                    b.status = 0;
                    score += 10;
                    document.getElementById('score').textContent = score;
                    
                    // すべてのブロックが破壊された場合
                    if (score === blockRowCount * blockColumnCount * 10) {
                        alert('おめでとうございます！ゲームクリア！');
                        document.location.reload();
                    }
                }
            }
        }
    }
}

// パドルの描画
function drawPaddle() {
    ctx.beginPath();
    ctx.rect(paddle.x, paddle.y, paddle.width, paddle.height);
    ctx.fillStyle = colors.paddle;
    ctx.fill();
    ctx.closePath();
}

// ボールの描画
function drawBall() {
    ctx.beginPath();
    ctx.arc(ball.x, ball.y, ball.radius, 0, Math.PI * 2);
    ctx.fillStyle = colors.ball;
    ctx.fill();
    ctx.closePath();
}

// ブロックの描画
function drawBlocks() {
    for (let c = 0; c < blockColumnCount; c++) {
        for (let r = 0; r < blockRowCount; r++) {
            if (blocks[c][r].status === 1) {
                const blockX = c * (blockWidth + blockPadding) + blockOffsetLeft;
                const blockY = r * (blockHeight + blockPadding) + blockOffsetTop;
                blocks[c][r].x = blockX;
                blocks[c][r].y = blockY;
                
                ctx.beginPath();
                ctx.rect(blockX, blockY, blockWidth, blockHeight);
                ctx.fillStyle = colors.blocks[r];
                ctx.fill();
                ctx.closePath();
            }
        }
    }
}

// パドルの移動
function movePaddle() {
    if (keys['ArrowLeft'] || keys['a'] || keys['A']) {
        paddle.dx = -paddle.speed;
    } else if (keys['ArrowRight'] || keys['d'] || keys['D']) {
        paddle.dx = paddle.speed;
    } else {
        paddle.dx = 0;
    }
    
    paddle.x += paddle.dx;
    
    // パドルが画面外に出ないように制限
    if (paddle.x < 0) {
        paddle.x = 0;
    } else if (paddle.x + paddle.width > canvas.width) {
        paddle.x = canvas.width - paddle.width;
    }
}

// ボールの移動
function moveBall() {
    ball.x += ball.dx;
    ball.y += ball.dy;
    
    // 左右の壁との衝突
    if (ball.x + ball.radius > canvas.width || ball.x - ball.radius < 0) {
        ball.dx = -ball.dx;
    }
    
    // 上の壁との衝突
    if (ball.y - ball.radius < 0) {
        ball.dy = -ball.dy;
    }
    
    // パドルとの衝突
    if (ball.y + ball.radius > paddle.y && 
        ball.x > paddle.x && 
        ball.x < paddle.x + paddle.width) {
        ball.dy = -ball.dy;
        
        // パドルのどの位置に当たったかでボールの角度を変える
        const hitPos = (ball.x - paddle.x) / paddle.width;
        ball.dx = 8 * (hitPos - 0.5);
    }
    
    // ボールが下に落ちた場合
    if (ball.y + ball.radius > canvas.height) {
        lives--;
        document.getElementById('lives').textContent = lives;
        
        if (lives === 0) {
            alert('ゲームオーバー！');
            document.location.reload();
        } else {
            resetBall();
        }
    }
}

// 画面のクリア
function clearCanvas() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
}

// メインゲームループ
function draw() {
    clearCanvas();
    
    if (gameRunning) {
        movePaddle();
        moveBall();
        collisionDetection();
    }
    
    drawBlocks();
    drawPaddle();
    drawBall();
    
    requestAnimationFrame(draw);
}

// ゲーム開始
draw(); 