// Get the modal
var registerModal = document.getElementById('registerModal');
var loginModal = document.getElementById('loginModal');
var historyModal = document.getElementById('historyModal');
var body = document.getElementById('body');
// When the user clicks anywhere outside of the modal, close it
window.onclick = function (event) {
    if (event.target === historyModal) {
        historyModal.style.display = "none";
        body.style.overflow = 'auto';
    }
    if (event.target === loginModal) {
        loginModal.style.display = "none";
        body.style.overflow = 'auto';
    }
    if (event.target === registerModal) {
        registerModal.style.display = "none";
        body.style.overflow = 'auto';
    }
}