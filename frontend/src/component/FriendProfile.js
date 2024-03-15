import {Link, useParams} from 'react-router-dom';
import {useQuery} from "react-query";
import axios from "axios";
import React from "react";
import {useDispatch, useSelector} from "react-redux";
import "./profile.css"
import {addFriend} from "../redux/auth";
import URL from "../misc/url";
import Loading from "./Loading";
import Error from "./Error";

const FriendProfile = () => {
    const {id} = useParams();
    const dispatch = useDispatch();
    const userId = useSelector((state) => state.persistedReducer.user.id);
    const friends = useSelector((state) => state.persistedReducer.user.friends);
    const friendIds = friends.map((friend) => friend.id);

    const { data, isLoading, isError } = useQuery("friendProfile", async () =>
        (await axios.get(`${URL.BASE_URL}/users/${id}`)).data
    );

    const handleAddFriend = ( friendId) => {
        dispatch(addFriend({userId,friendId}));
    };

    if (isLoading)
        return <div><Loading/></div>;

    if (isError)
        return <div><Error/></div>;

    return (
        <div style={{
            color: "whitesmoke"
        }}>

            <main>

                <header>
                    <div className="tb">
                        <div className="td" id="logo">
                            {/*<a href="#"><i className="fab fa-facebook-square"></i></a>*/}
                        </div>

                        <div className="td" id="i-links">
                            <div className="tb">
                                <div className="td" id="m-td">

                                </div>

                            </div>
                        </div>
                    </div>
                </header>
                <div id="profile-upper">
                    <div id="profile-banner-image">
                        <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQKCFeB3nqGOcE1NWGQyghA-ebm1URHmJQYLQ&usqp=CAU" alt="Banner"/>
                    </div>
                    <div id="profile-d">
                        <div id="profile-pic">
                            <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSY3-jXkdGt479-LGLdQMet805Y1lQlgH0CBg&usqp=CAU" alt="profile"/>
                        </div>
                        <div style={{
                            color: "#00ffcc"
                        }}>{data.firstName} {data.lastName}</div>

                    </div>
                    <div id="black-grd"></div>
                </div>
                <div id="main-content">
                    <div className="tb">
                        <div className="td" id="l-col">
                            <div className="l-cnt2" style={{
                                backgroundColor: "#330000"
                            }}>
                                <div className="cnt-label">

                                    <span>Bio</span>
                                    <div className="lb-action"><i className="material-icons">edit</i></div>
                                </div>
                                <div id="i-box">
                                    <div id="u-occ">Don't be sad because sad backwards is das and das not good.</div>

                                </div>
                            </div>
                            <div className="l-cnt2 l-mrg">
                                <div className="cnt-label">

                                    <span id="me">Friends</span>
                                    <i className="text-amber-900">({data.friends.length})</i>


                                    {data.friends.map((userFriends) => (
                                        <div key={userFriends.id}
                                             className="postk2"
                                        >

                                            <div className={`post-text text-black ${userId === userFriends.id ? "hidden" : "text-rose-500"}`}>
                                                <Link to={`/profile/${userFriends.id}`}>

                                                    <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSY3-jXkdGt479-LGLdQMet805Y1lQlgH0CBg&usqp=CAU" alt={`Profile of ${userFriends.username}`} className="profile-pic" />
                                                </Link>
                                            <div
                                                className="post-content"
                                            >

                                                {friendIds.includes( userFriends.id) ?

                                                    <p >{userFriends.firstName}</p>
                                                    :
                                                    <>
                                                        <p >{userFriends.firstName}</p>
                                                        <button style={{
                                                            backgroundColor: "#3bfcd9",
                                                            borderRadius: "70px",
                                                            color: "#10133a"
                                                        }} onClick={() => handleAddFriend(userFriends.id)}>Add</button>
                                                        </>
                                                }
                                            </div>
                                            </div>
                                        </div>
                                    ))}

                                </div>

                            </div>

                            <div id="t-box">
                                {/*<a href="#">Privacy</a> <a href="#">Terms</a> <a href="#">Advertising</a> <a href="#">Cookies</a>*/}
                                <div id="cpy-nt">Stacks Of Laughs &copy; <span id="curr-year"></span></div>
                            </div>
                        </div>
                        <div className="td" id="m-col">
                            <div className="m-mrg" id="composer" style={{
                                visibility: "hidden"
                            }}>
                                <div id="c-tabs-cvr">
                                    <div className="tb" id="c-tabs">
                                        <div className="td active"><span>Make Post</span></div>
                                        <div className="td"><i className="material-icons" >camera_enhance</i><span>Photo/Video</span></div>
                                        <div className="td"><i className="material-icons">videocam</i><span>Live Video</span></div>
                                        <div className="td"><i className="material-icons">event</i><span>Life Event</span></div>
                                    </div>
                                </div>
                                <div id="c-c-main">
                                    <div className="tb" style={{
                                        visibility: "hidden"
                                    }}>
                                        <div className="td profile-pic" id="p-c-i"><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTYZxm98EmwJ1v1onwGLXl6sNqt857dqUmEOw&usqp=CAU" alt="Profile pic"/></div>
                                        <div className="td" id="c-inp">
                                            <input type="text" placeholder="What's on your mind?"
                                            style={{
                                                visibility: "hidden"
                                            }}/>


                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <div className="post-list">
                                    {data.posts.map((post) => (
                                        <div key={post.id} className="postk mb-3">
                                            <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSY3-jXkdGt479-LGLdQMet805Y1lQlgH0CBg&usqp=CAU" alt={`Profile of ${post.username}`} className="profile-pic" />
                                            <div className="post-content">
                                                <p className="username text-sm text-gray-400">
                                                    {`${data.firstName} updated their status`}
                                                </p>
                                                <p className="post-text text-black">{post.content}</p>
                                            </div>
                                        </div>
                                    ))}
                                </div>

                                <div className="post">
                                    <div className="tb">
                                        {/*<a href="#" className="td p-p-pic"><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSY3-jXkdGt479-LGLdQMet805Y1lQlgH0CBg&usqp=CAU" alt="post update"/></a>*/}
                                        <div className="td p-r-hdr">
                                            <div className="p-u-info" style={{
                                                color:"#a8a8a8"
                                            }}>
                                                {/*<a href="#" style={{*/}
                                                {/*    color:"#f1861b"*/}
                                                {/*}}>{data.firstName}</a> shared a picture*/}
                                            </div>
                                            <div className="p-dt">
                                                <span>25 minutes ago</span>
                                            </div>
                                        </div>
                                    </div>
                                    <img src="data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxITEhUREhIVFRAVEBYWFRYVFRAVFRUVFRUWFhUVFRUYHSggGBolHRUVITEhJSkrLi4uFx80OTQsOCgtLisBCgoKDg0OGhAQGi0iICUtLS0tLS0rLS0rLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLf/AABEIALcBEwMBIgACEQEDEQH/xAAbAAABBQEBAAAAAAAAAAAAAAAAAgMEBQYBB//EAD4QAAEDAgQEBQEECAYCAwAAAAEAAgMEEQUSITEGQVFhEyJxgZGxBzKhwRQjQlJiktHhFRZDcoLwM/FEY7L/xAAaAQACAwEBAAAAAAAAAAAAAAACBAEDBQAG/8QAOhEAAQQABAMFBwIFAwUAAAAAAQACAxEEEiExQVFhBRNxgZEUIqGxwdHwIzJCUmKC4UOS8SQzcqLS/9oADAMBAAIRAxEAPwDxILiF1XAIULq6AgqytEK4i6F0LgpRdFkqy6AjaCotcsu5EoORdWhrUNlNkITl0nKhcwcCptcaFIjjSWNTzQmIYxxVb3JxlOClGlCnYdh003/ije/uGm3ydFNrcHlhF5PDB/dEgLx6hbEbcMaaav4pF+Ja1+QuF8r1WekpAoksRb6K4cQrqhxSkY0MfRMebWL3DO4nmbHQJXG4FlXGNeisdiHsbYaXdNPqsSUK0xqKDPenz5TuHgAD0tyVXZYjmOG4pOxvD2hwBHjoULiVZFlFI0myLJa4pyqLSbLi6iyGlK4upVkLg1cSkoS1whTlXJKF1cQrkFJXSuISpQhKQupcuBdXAuqQuXWlLDbrkbbqU1iYiZmVbnUmBGleEplLTOkcGMaXPcbBrRcn2WmHAFXlu4xNdbSPO58l+hDQQPlWOEbP3EBLy4lkQt7gFjTGkELUDhSYECaSCBx2ZNKGyH0Y0E/Nk1V8L1DXWDRI395h8nu59rKCGHQH88dkHt0AdlLxf5x2WbuhTa2gfGbOAv2c134gqE4ITaaY5rhYK6ClNTYTjFLSSaUlPsC1eG8PQNaJKuYgWuY47ZvQudoFnaOQMOYi5Gw5X6lOVeISSaX0PIc/6plzXnRpr5pGdsryGsOUcTp8LWkruMbN8KkiEMQFgSXOdbrYaArNy1Tnkuc4ucdySSVY4ZwlUzWJyxNPOZzmOI7RtBefhaqD7NGht3zSE9msY32Dszvmy5mKhw2gofE+qHD4eMX7MwvJ3LRdnq7b4jwWHpyE9Kzop2O4A6nfpnczq4tP/wCQE5S4Q59rkWPutGHFx1nvRaseAxEg7vIQRwOnh+C1reDMBoiy8jI55OZcc7QejW7e6ica/Z+5zHVEAiGUE+HHF4dx0uDqVGZjcdC3L4b3G2hbax9+ShzfaJVOBa1jGsItqS4/0XnJji55nFgJHC6TrOzsDhABM/NJxJuxf8oGg6aLz8ITlRfMSdySfk3TauNg0VnriEIQlckpQC6xis6KjjJ88gHzf8dETIy5A+QNFlVzYinW0xWqiwCleBlq/DP/ANwaW/zNOikz8HTNbnjfHM3rG6/xp+adhhhupDXjY+O3xSJ7Ri4mvEEfPRYx1MUy9hCuqmBzSWuaWuG4IIKgyxq7EYJrRbEyyW1CSCE7IxIBWU4UaKYCQhKIXFWRW6JCEIXLlxdQuIQFyl0cJdewJsLmwJsBzPQKU2O6saCu8GDw4j55W5pnW2DtBEPbU+qYpHAmw6LWwYb3eZ+g4cyk2OfLIW1QugefWuHTproikqnwkmN5a4ixI3t0B5IoZaqV5ZA6YvcbuyOkub83EH6qLMNT6r1HgOkibD5b3P3js4nYm4/DoqcdiGwtzBv51O6mPCyzPywMBcdydgBxJ38BuSsDNgRidaeaNj9ywOdLKP8AcGgge5RLRttpKXerHAfiV7S00sI/02DmSWD3JUKTiCh5SRHuC0j52SMXbRYP2k+Y/wDk/Na7OwnHR8rr/wDFoHlZJ+K8XfCOoUaWnXpuO8WUgGWKKOV/XK0ge6wE8pe4uIAudgLAei2oHnFNzPjLRws7/nVIYrDswxpsuY8QBt52R5KmkjsuxqbPHcKI0WKVlgMTxyVTX5gpEMZcQ1oJcTYAbkrfcK8Iah73+bmW8uzep030WSwGF7n+Szf3nmwsOYBOy3v+aaSlYGiQyvA2Z5h/N90fKSx80ukcW53oWU3h+zROO8xDskQ4bF55XwaONWTstfTUkcLcws1o6n8XE7n1Wbx37RaeIlkV5nj90+Uf8tlgeIuKJqs2Plj/AHQd/Uqiayyow3Zb36yk+HHzKfn7TZGBHAAQBQ00HgPqd+S0GLcVzz3u1rG9NXH5Kh4fib4zY+ZnTp6Kvahy3G4WKOPK0aLKfjsQ+QSOebC2EVbHK3e4O7LfVV1Zgw+9Hf0NvwVHT1RYcwU52Nnr8Klj/Z3aHRaoxcGJiy4huo4j6H6bJ2PhmeZuZrLADc5Rss3LGWuLTuDYrVYVxHLHfL5mnkeRVDjkxfK6QgAuNyBtdWY2Nzh31Cun1ST/AGMRtEJdnvUH6EaKvUmipTI4NFrk803DGXHQE+gWhwTHJKX7kcRP8bLn5BBSLYnuGZovzpVM7vNT3Fo5gX+fmi0mF/ZtnaC+SQHnbw7fiCVKrfsxaBdkswP8TWSN+G5XfVNUv2oTN0dTRn/a5zfwIKtKT7V4ibSwuj7izh+H9Eo52OjNuBroAR+eStGBwbz7szr6ur4Fgb8Fh8U4Xqae58r2jnEXuIHVzHAOHwq+mrZGG7XFp6sJH0XsNTxBBMy4s5p2209DyXmPEkUYlJZzN9efvzWj2b2k6Y92dD8PRV43sqfDMDnkPYeNajxGoI6ivBMVGMPlZkl85/ZcRd7fcqtISi1ILrarVD2jSqWeyARimChySHw3UGaOxWzwyiiq2lrcsVSB5dhFJ2I/Yf3GizmKUzmOLHtLXtNiDuClsXBGWEjQj5c+oXYfEZnlh0I3H16hVu4SbLoQVkHUWnVyyEIUUpXEWQpWH28WPMAW+I24Ox12PZCuN1oLU2iwaplbnjikczrbQ+h0ulUsL2y5C0h+oI0XsmI11NTxN8XJ90WLgLejQdh2CxE01HNJ4tMMrgbE5S1pJ30KCHHF+jWGvzf/AIT0PZM7HgzSMB/lAN9aJOv+0WsrVwFryHabH5Vq/il7IhFC3Kebj+QUXF4y57n3vrYeg0VZZa0uEbM1okG3paVdiJcJLI2M1Z30ur5pFRI+Q5pXuee50+F2KEdFNoMNkmNo23/AK3jwTwiPHtr0JsPdFG2OJ1ceQXRYHE4r3wPdP8R29dSqMFSYKSR/3WOPoD9VoPGp2Dy5AfcpQ4gjaNyezbppuJcdGM9U4OxsPH/38QB4V9T9FQuw54BJFrbgkX+FVzRaq+rMYzElrBqP2tfoVUOdc3V7v1BTwszFMw8bh7O4u3u/mNAo+Q7anspMVBI7Zh+n1V/QVUMYF7C473TcmOsaXW819rLNmlMb8gbYWlD2bhzGJJpgL4CvrfyUODh+VzcxsB6gqU7hyzcxk9gFBlx6SxaLAfKjOxSUjLm09kIfOTRIGvJHn7KjFBjnGuN7+oT1bStYNCSe9lWOKddJdITJsirWRNIx77Y3KOSatdOwxXIAFydgNSUuGPMbLbcNU7YyCxgL+truScsrcP7xFpHFYsQMurPL/KoaXh2qdqIXgdXAN+puqjFKZzSWuFnNOq9upsLqJG3Nmj1/JYPjvBQz9ZnaTexH7R31HVHDjvaAYnNqxp4rOwnaMplaJmhodtodfVUuAYlCwBrrjvYn/pVzNh0EwzRg35W+qwrFZ0uJSMFmnRdHEd2Gj8F7fDdrMDO6xLA5nDT8+CXiNCY3ZdT3sq90atZMXe4WNlXuctIMDm+/usnF9wJLw5OU8CKrp1XKZ8jD5HEei0f6IJIxn/8AJvdU+HsF7nlstBC/TX+yTfh2B1t3Wp2Yx7mHNq08Dt4rP1lA9ncJikoXSmzXNB6OzD6BXmI1pY24ANz6iysuGsQp5CBYMkH7Jvr6Hmk8ZO+FuYtPipPZ0MsvcskyHeiL9OB8Fm3UFTSkTAXYD9+Ml7B/uPL3CsOIqhtXTioa39dGQyUDcMtcP7sufa5Xp8NFHIzYZrfPYtvqvNsdw1tNM50Vw03bJG612B37QA+9CdB2+lGGx3fx5TuNvqFgY/s7EYWVrpQDR0e0UD/S8btvgdQTtqsK5dS5xZxHcppV8U7wXUIQpXLiL2XAgoCpVjU1sk7s8ji52wvsLaaBaTCIvDYXFuwv7rM08Tg0OynL1sbfKsxjXlyO6W0TbSIogQLTWEmEczpJLutL/wAqY8B40cB6lVc8OU7g+hUF9yfKdO6AHJw4rNqGlKTSCQatF87Py2VxheJOhdmbr2UrEcdkmblcGgfj8qiaSnA5GzI52cjVCMbiGxdyHnLySilxtJ2TV082YjbRXh16pVrW8VMjohu94aFIb+iN3LnHsHAKpukuKsLtEyzEsj/ZE3+63fPT4JyvcxzvIC1qaMN9t02ZspXH1R5FJuljs5hardbjm28Bp/hDwbpCSJCjMl84JtSWjglgpV01dKBRB6EhWuDwsLs0krY2cyTYn0G62VPxrR0wtCx8hH7rQ0E9S91l5ylAKl2GEjsxNpGfAsneHPcdNgDQ+59VrsV+0atm8seWJvYZn/Oyzs75pDnkc956vJP/AKXKN1irdrhbVa2CwEbRaghkJ9xmvPc+ps/FZp+hTjCncRYA7RMRlKvHdylqdBzNBT900+6s6HDS/c2Vo7hJ7heN4J6O0+CukxEWxclX4qGM091LMRVGXdWVLiGmhuo2J4XNCbSxuaOtjkPuNFXc9NEoXOBzNK08NiqAcw2OYP2VvWVWdV8rF2F3VWbsMeY/EbqOY5j+q0SRLGLQ5ZZ5HOAJO5rkpvDPGE1O9rZCXxE2vzH9VreOAyenE8drtFwRzafvNPYheZPYrfD8acIX07zoWnKe6wcVgHQyiSPnqAtXBY9ssboJzYIIBPhsT8uqzUrrlICCUNU8VmDZdQhCJSkhBQF1VhSrWOuvC2H911/bW31UV7LlMROsrnA5QJATvy9Uyxw7vXgqsTM8++daFDwGymYZwtUSgODQ1vWQuH4AFP1fCs0Yvdjv9ub81uKKnnkF3OLGW66/2TVe+lpxeWWRzuhkJv7BLtxkziMrRXKvwryh7VxBloUejRfxXmc9O5hs5pHt+abCvscx1s12xxNazqbuefnZUK04XSZbeK8Fvwue9gL25TyXF0FIJXbqzOrqS7rhKRdIc5QZVwakvbc902SRolPKav8AKRlcAdN1c20sFdum0pCHrqS7roKRdF1PeKKTgcl51Huu2KIYitlGUJ9s+qmfpwA3VZICNwbpsFWxY6RmygwtdupVRNmKcppMpBsDbk4XBTDAnmhWC3kudraggAUtvgeP0LrMqIBH/E0AN+dwtjDgsMrc9JUt7Ndq3+Yf3XjggJ2Xaasmgdmje9h7aA+o2KWxOBFWBSyX9msN92fIkkeR4eh8F6lWiWMFk8d2+zmkdjssNj2Gwjzw+Uc26Ee3RXGEfaCHAR1Te2durf8Ak3l7KNxIInN8WIgsPNp2/wC9Ephy6CQA3RSWFilw2IyuaW31tp/PVZG1lcYTi+SzHfd6/wBVSl6QXLWdI2q4L1WGxEmHk7yM6/NajGKeOQ3jsDlv2ustPpcHcJ6nqS1176Jqvlu6/XdLGQhuW05i5YsQe9Dcrr1A2UQroSV0JVKIuhCFC5cXUlKQgqV1T8NqvDe2S18pvZVycjcrGEXRQPaHNIK2FXx1UPbkjYxnfzErPzGR5zyFxJ5m6dwwaqdVjyrUw3Z7O77y1mRshwzssTAL48VWNCCFwypp0ql8jAN00GkrpK4XJvUp5jAN0jJOAj0CbDSVx7bJ58ifoMJnqHWhhfIf4Wkj3dsFRnc427QLs1anQKE5uijNXpmF/ZjKWF9VIIgB91lifd2wWHkp44qh8b7uja8i43IvuokmY40zWuSCPEsc4tbZNXtwulXhLCvZRQ2GUSX9im5Yqe125/5d1R3p/ld6Ifar3Y4eIVPYJJKmlrOYI9RZPRxwW1fr7FEX1wPorDJW4PoqvMrPh2iEs7GuIDM13X6NN1GqPDJsy57kWV5wrgBqH3OkLNH8s38ARad2Xu0CrxUzWQucTl034+nPkoPGVTHJUnwrZGtDLjYkE3sqRoW74m4ZjLiacAOY0AsHM/1ssZNA5ji1wIcNwd1GHy92MnD1QdnzRPga2PgBod1yM2UmKxUYFLidZaGHxFEByYcLVxCBZErARqobKhJmqVunFRhuqUEbrUeoYAdEmOUi4BsDuOR9QkGS6QSsKRzSSWp0DSilkpF0nMglVF6IBDkhBKSSqnHVGAgoQhCiQhCFy5cQhdVa5CEIRBQplHUZSrCSsBG6pQUsOT0OMkjblGyofC1xtOP3XAE9SMzB29xrp07pL4e6ULrNKbANFJDlZYThMk58os3m48vTqqoNVrg2NzU9xHY35EE6qmSOQj3N+qqnEndnuqzdV6Lw1wzBEQXRNkd+9JrbuOS0+K8V01IzzvaHW0ijy5v5RsvLW4bilWRI4SsYdiXPhYB2YtTgn2cQjz1L3yO3yjRnufvH5SRw8TTmlkvoNT67BYsYdGf1ZcxOumteZ0H5osxxJxtUVTsrCY4uTW6PP+4qiiw6Zrw/LZx1Akvr8r0jFMYoKBpjZFG6Xk1jRdo5XXn/ABBjTqghwbkDem/ytBtyR1GzK3meKawsr5KEcdNN2Tr9r8iRyKsKfGJYz5qSM25hpVn/AJpa6wNLKPRoP5rJU2NzN55h3up8HFDgblgST8J/QD4EhVzdnXr3YJ5hxHwK0ZxyjcLPbkI5Sxi6oa6uo3XsweoaBdSXcQU8os+IA9bD6qDWNosji0+e2gHVDFC1h1a4eBFfBV4bDCJ1Oa8HxseoVZlaSXNFm8loOF462SNzKazI7kmRwcD/AMCs6w2b7LUx8VyGNsNJHlIh81x0GuUBa+MZliYxrb4m+CbxokczIxoOu7joK4m90uiEtDUD9IcXQzfekN7Ak2Bze3wtFXUFJVN1Eb7aB7CM49HBd4R4hiqqZ1JVAOkAyhrgP1jOo7qrxbheopD41HcsOronauA7D9ofikXgF2VxyvHHgevT5eCyJ47lGZ2SQDcaNPK+XQ7FZnHeGZILuZd8XXTM31t9Vnw5b+g4tiePDqGGN+2cDy/8gdQstxBFF4pMTg5p1uzZFD32fJI3Xnz9NFs4OfEX3WIbrwPA+e1qpzLjrri6Cr8xWlSQi6cSHKLU2koC4SuXUIk49yaTsMtr6AktI1F7X5jumijcb1XDRCEIQokIQhcuXEBdshAF1oQAu2QjAULqFxKsiGihWGBzlkoI6bHYjorPEzTu1y+FJzFsv9is8w2NxurmDEmPAbM0H+Lml5We+Hi/Lf8AyksREc4kbfWjr6cVDjYL2BuFIoKrwJRIBe3p+F1JroKdgY+F93E2LdNuqjOYMzS4XAdqBzC0Y2tmwx02UB7ZW6g0bGuhWvH2hSvsyOF0jhsHebXrZicmjxqrF3nwIugzRG3oLuKcw7H6aFrfBYGu3I8sYv3PNN4txZVzDK2WljHZ5Lv5islrC0+5EG9XWSsQEi8keU7DMSemxsacspVVNwkGayzi/PXf3IVdMI2AsDszfqiWgnkN3VETyesxK4MAn5ZHekgK0YXAH9SQHpsnY5B/qzA/AKmmj1JA0TKv3YLUc4n+wv8AQqLVYa5v3mPae4ITpwgeLjIPmFoMxUbtA4HwKql1g1CXJEQuRN1SvduzZSExeienOisOGq4QTB7hdhBY70PNQKjktbw/wHPURtlJyMcLj96yPFZLIkNDZJzuiERbJs7TSyfKlPrcAjmIlgfkefMHN2PQ6fUJ+k4nfDenrsxc0fq5WAOuNgHX1I7/AIK6w3g+OkGaSqeL8iW29hyXaqppnOtZryNnOAVMUHfNyUXtGxqiPAleclmI/TkBkbwvRw/Ovosxi0VNWtLmECYbOsA49nDmFlv8Hnay5jfbrlK9LnqiwXEDA3qB/ZUOKY64i2gHZaGGwDm/tFDq669KV2Dxc7Rkjb7t8TddNF53K2xsQpEcrf8AoVqGx57yNBB63/JJmoYCbtc0C+29kricBIxxrXwXoDiGmg4FV0nhnt6KA/fTZTcRYwGzPnSygJJrSN7HimYv23r5oXQ7ohJRWrUu1vVIQhCppCEIUrlxC6hQuXUAJxrE7GACCRpfZWNYUBcmGR3U1lCOqvahsNS4RwRsaQwEu+7z/uqmSF7CQQSAbXG3yrcN3btHt15XsqnudW4vSxvV7C9r8E82hhI1cWu9AQo0mEP3jIeO2/wn2OupMOhuNCmz2fn1idXjqPule8kZsfXVUToyDYgg9DougLXxVcbhlnia9vW3nHuUis4bje0yUr723YdbehGo90m+N0BqVtdd2+vDzpCO0Gg1IMvXdvrw81nKCjfK8MjF3WJ9gLkqdUQPb94WI0PqOSisMkTwRdkjT6FWVHXDxDJK3M3zHLckBxGh+Uzh87XkDavO0xIfdc7fagOO967cq81XNnSw9W2A4bHUSPOUkNaSGNuC5x29ALKx/wAhVLjdmUMy3Gckm/TT6rm9ohrsrjVc1XM6KInM4CjXjx0G5A4kDRZoFPROI6j0Wlg+zmrIveP5efyUhn2fTDV07G/P9U1H2hAd3D5/RJvxsA/iP+132WfgrJB92V4/5OVlT43K3R2R4/jB/JWA4NA/+XFfuP7pB4NkJ8lRA49L2/qmPaMI4aj/ANSPok3y4STevQ/ZMTtoZ2kvHgv52sB+CzNfQsjlyxvEjLXzDvyWmm4Oqht4R9H/ANlFPCVV0Z/Mg/6a7EnkT99UWHxEEP8Aracifvr8VnWx3eNLi+o6gcl6DUcZSGJscbRE1osebj6LM1PDFTGPEIZ5ddH/ANVWsqHk2a25Usjwzj3jwHUdDuAm6bi67o5q00PPgraqxNzjd7ifUqBNiwGyTFhr3tMjzdgdYgdlKmw6FkrbkeG62U30v3USdpge7GE5H2XTASABpXDRwJafA1QPPRMNxipe3w25iy97W/NWWC4C+oLg52V41APNcfizIDlDQTbla2Xkqypxh7iHNuxw2LSQUu2fEyAge7fH/CuOHwghuJ2p5CvPXfqD4LaVUdIIhHOGtkbu2xuQNMzfx2VFiMeHZSYyc1tLF30Kz1ZXySWzuLiOZUPOujg7vd7vAHT7rO9jzEPstPENNNJ51Wl8U5KwH0VdPFZT86jzG6LFBj23xWhGSCoZK4lOaklqxqTIKEIQoUriF1C5chCELlyk2SghCcpUWnIpS3UEg9lraGsZJFHTsIBdpJcWOmhdfbZZAJcMxaczTYoZYg8dRqPFS00fkd8pOmYDYkC6tWdfhoa+UwuvHG613HU36dUy6V8ZDZGkXAIvvY7FLgxG8YhIAaZA5zuZVhU1sLpi4gFjG2aHa3NtPjdFFNLDzO/ntV8NfBd7O17dDtW++poa8wAXOOu+ihNqWnY/Kl0tc+J2dhsfr2I5purwljKWKbN+tlk8rb/6QFhcctbKPi1E+neI3G5yNdtYjML2I5J6LtFkltdxseNb6ckkcOHCq0N78QDV+B4XupGK4k2ZhzRMEgN8wBGnRV8Ys09006QnRN3QsyRk5B+H1RRwhjcrdAtfwbiIpCXCLPIf2rlvoNtldzcbzl5cXBrP3GWFvc6rCQVh2yknsnneKdAy3lzd7K4Q4Jpz5QSedk/FUtwsjpLAsk3zsgctb0G1UtLFxLI5xMr3OaQbAvdpfY2T2EVBcXyZS5g0vc2BIva/Wyy0eHSOv5vu5b891Nwuvljjnp26sc6/SznZW3PazQukxrGtIiA4ca0tNYbAuD4tAASXA5b0H7uFnaq58FNbirGZw61ztopkWL036OG6eL4ma/bsqmjw5ro5HzHNM6Xw2fwta0agdybe3dON4Pd4HjeMAHZsrQNSWaOJPIb/AAgd2oy/e01A46n8+qrHZzXi2gkfw3QNO2dXK712FE7K9xCaOOGF2az3tc4+axty0CpZscdfR57WJUCvwaSNokzFzM4YTqbaX3VvPwzko/0ppzubNZ7ejLaaD1BurmY2INbmN2a8726KqXsow33g/aQ13IE8/qdVGbxHN+9cdHJ+lxyHMHSU0bjsS1rASD33U2tjp/8ADHENaJfFY8EWvtlNj6Ei3ZY9knf5XMMGKDtKokctvRBJgPZ5iMuVzToQSOoII4G1onwxTOc6mlMTyQSxxBaba6t39ws3iNDLE60g57jVvslB4VrT40Qzw5WCVltM5OYds/T1UeyGPWPXod/X7oQ6ePYl42onUDfQ7b60VnEOU3EPDMl4mlsZA0cbm/NMTs1FuanKct1XRNtddHZLpKCaa4ije+2+UF1vU8lZR8G1pt+qAv1e38louGuJY6WIMjpy54GrnOAFyNSbDVNVXGFU79to7NZlsle4xkjj7rWj+o6+gtIvxOJJORoGvHlz0PHoFDg4Bm/1ZI2dgST+ICePDVDHpJO5x7Ob+SrajEJHnM95J7lRzWAc0y3s41+rKfIAD7qgsxbz70p/tAHxpXIwbDdw6T3ukVNNQtbYMJ76KobijUzLO54JaDYbkclPsWGbu8n+5E3Cyl3vSP8AN3/CrsQpG5jkBDVXOiIWxk4ec6ETMfc2uWnf2IWdISBhglJ7o1S1oZ928W6EHcKtQpskAKiPZZJywOj3TTXgpKEIVKNSQV0IQmwqUpdQhGEKF0LqEYQqZQ1pa9j3DO2M3DCSBpqB6XRilc6eV8z7Znm5AvYX0AF+QCELhG3NmrXby3+a4734DyGyiBKQhWhQpmGTNY+7h5dQbWuL9FYVGKiOYPhsWmFrXAgj1CEKDG0us9VczEysMeQ1lJII3s0mzi5zBzW2JjDXjkTfQhM/4g4Pc8AAuABAGmmx9UIV7IIxwVQmkblpx9266ZtT6qRV4sXPzMGUHJcHXzN1v7qTBxHKI/DIDmgOLdSMpe4ve63O5OyEIvZ46ot2QxSPgIyH9oyjj7p0I14aqyONZqJrLAmMAvJGpebEEFM0FRJFRh4fo+R12m5u1rRcHsenYIQl3RMaMoGhfr8Snxh45RGx4sOa5zr1t2upvc6BVssF6ZjrWu4nS99gbEXtZQJaYBjXB13OvcW0HTXnzXUKGPcTv/EVSYm5I+rL3O+/Hhrsl11C+EtD7eaMPFjfQ3/HRNTBzTZwtbloe/JdQmcLM5zATxB+ay4nl7GOPEWmJnJrxDob7IQpnccyZaNFKbiHUH2XHVTj91vK/La9kIQPxctbo4oGF4FflJZoXlpcXbG3vYEfVJp8OLrEnQ/nshCzXTPcCSU/HAz9Pq2In+51FWNNhkYZmNyRuDsQpGH4q2lc5pjD4X7jW49LrqFMI70lj9QUOKaz2aP3R7wF6b3mv5Dz1UauxYNv4Bd4T9cp0ykm5AtyVEXX1XEJlrcmySbeUAm60s70NrPGuqAU1O1CFbJqw2uG6i2XEIWTlCatf//Z" alt="friend post"/>

                                    <div>

                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="td" id="r-col">

                        </div>
                    </div>
                </div>
                <div id="device-bar-2"><i className="fab fa-apple"></i></div>
            </main>

        </div>
    );
}
export default FriendProfile;
