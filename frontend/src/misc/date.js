const today = new Date();
const year = today.getFullYear();
const month = String(today.getMonth() + 1).padStart(2, '0'); // Month is zero-based, so add 1 and pad with 0 if needed
const day = String(today.getDate()).padStart(2, '0'); // Pad day with 0 if needed
const formattedDate = `${year}-${month}-${day}`;

const DATE = {
    formattedDate
}
export default DATE;
