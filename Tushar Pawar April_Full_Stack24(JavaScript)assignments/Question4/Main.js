let sessionLength = 25;
let breakLength = 5;
let timerInterval;
let isSession = true;
let timeRemaining = sessionLength * 60;

const timerDisplay = document.querySelector(".timer");
const startButton = document.getElementById("startButton");
const stopButton = document.getElementById("stopButton");
const resetButton = document.getElementById("resetButton");
const decrementSession = document.getElementById("decrementSession");
const incrementSession = document.getElementById("incrementSession");
const decrementBreak = document.getElementById("decrementBreak");
const incrementBreak = document.getElementById("incrementBreak");
const sessionLengthDisplay = document.getElementById("sessionLength");
const breakLengthDisplay = document.getElementById("breakLength");

function updateTimerDisplay() {
    const minutes = Math.floor(timeRemaining / 60);
    const seconds = timeRemaining % 60;
    timerDisplay.textContent = `${minutes.toString().padStart(2, "0")}:${seconds.toString().padStart(2, "0")}`;
}

function startTimer() {
    timerInterval = setInterval(() => {
        timeRemaining--;
        updateTimerDisplay();
        if (timeRemaining === 0) {
            clearInterval(timerInterval);
            if (isSession) {
                isSession = false;
                timeRemaining = breakLength * 60;
                startTimer();
            } else {
                isSession = true;
                timeRemaining = sessionLength * 60;
                startTimer();
            }
        }
    }, 1000);

    startButton.style.display = "none";
    stopButton.style.display = "inline-block";
    resetButton.classList.add("reset-active"); // Add class to change reset button color
    resetButton.disabled = false;
}

function stopTimer() {
    clearInterval(timerInterval);
    startButton.style.display = "inline-block";
    stopButton.style.display = "none";
    resetButton.classList.remove("reset-active"); // Remove class if needed
    resetButton.disabled = false;
}

function resetTimer() {
    clearInterval(timerInterval);
    timeRemaining = sessionLength * 60;
    updateTimerDisplay();
    startButton.style.display = "inline-block";
    stopButton.style.display = "none";
    startButton.disabled = false;
    resetButton.classList.remove("reset-active"); // Remove class if needed
    resetButton.disabled = true;
}

function updateSessionLength(value) {
    sessionLength = value;
    sessionLengthDisplay.textContent = sessionLength;
    if (isSession) {
        timeRemaining = sessionLength * 60;
        updateTimerDisplay();
    }
}

function updateBreakLength(value) {
    breakLength = value;
    breakLengthDisplay.textContent = breakLength;
    if (!isSession) {
        timeRemaining = breakLength * 60;
        updateTimerDisplay();
    }
}

decrementSession.addEventListener("click", () => {
    if (sessionLength > 1) {
        updateSessionLength(sessionLength - 1);
    }
});

incrementSession.addEventListener("click", () => {
    updateSessionLength(sessionLength + 1);
});

decrementBreak.addEventListener("click", () => {
    if (breakLength > 1) {
        updateBreakLength(breakLength - 1);
    }
});

incrementBreak.addEventListener("click", () => {
    updateBreakLength(breakLength + 1);
});

startButton.addEventListener("click", startTimer);
stopButton.addEventListener("click", stopTimer);
resetButton.addEventListener("click", resetTimer);

updateTimerDisplay();