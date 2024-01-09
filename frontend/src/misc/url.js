const  BASE_URL = process.env.REACT_APP_SERVER
const SIGN_IN = `${BASE_URL}/users/signin`
const SIGN_UP = `${BASE_URL}/users/create`
const FETCH_ALL_POSTS = `${BASE_URL}/posts/all`

const URL = {
    BASE_URL,
    SIGN_IN,
    SIGN_UP,
    FETCH_ALL_POSTS
};
export default URL
