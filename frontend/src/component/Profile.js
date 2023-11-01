import React, {useState } from "react";

import "./profile.css"
import {useSelector} from "react-redux";
import {useMutation, useQuery, useQueryClient} from "react-query";
import axios from "axios";
import Modal from "../misc/Modal";
import {Link} from "react-router-dom";
import DATE from "../misc/date";

const Profile = () => {
    const [post, setPost] = useState("");

    const [isModalOpen, setIsModalOpen] = useState(false);

    const openModal = () => setIsModalOpen(true);

    const closeModal = () => {
        setIsModalOpen(false);
        setPostResult("")
    }

    const [postResult, setPostResult] = useState(null);


    let status = {
        content: post,
        createdAt: DATE.formattedDate
    }

    const { mutate: createItem, isError: errorCreating } = useMutation((newItem) => {
        if (post.length < 1) {
            console.log("status too short")
            setPostResult("Status must not be empty")

            return
        }
        return axios.post(`http://localhost:8080/api/posts/${userId}/new-post`, status).then((response) => response.data);
    },
        {
            onSuccess: (data) => {
                if (data === undefined)
                    return
                // `data` here contains the response from the server

                const result =
                    "Success"

                setPostResult(result);

                console.log('Successfully created item:', data);
                // You can update your application state or perform other actions here.
                setTimeout(() => {
                    // setOpen(false)
                    // setResult("")
                    setPostResult("")
                    setPost("")
                    // setOpen(false)
                    closeModal()
                }, 2000)
            },

            onError: (err) => {
                console.log("Error creating", err);
            },

    });

    const handleCreateItem = (newItem) => {
        createItem(newItem);
    };


    const userId = useSelector((state) => state.persistedReducer.user.id);

    const userFirstName = useSelector((state) => state.persistedReducer.user.firstName);

    const userLastName = useSelector((state) => state.persistedReducer.user.lastName);


    const fetchUserPosts = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/users/${userId}`);

            console.log(response)
            return response.json();
        } catch (error) {
            console.log(error)
            throw new Error("Error fetching data");
        }
    };


    const { data, isLoading, isError } = useQuery("userPosts", fetchUserPosts);


    const { data: data2, isLoading: isLoading2 } = useQuery('data2', () =>
        axios.get(`http://localhost:8080/api/friends/${userId}`).then((response) => response.data)
    );
    console.log(data2)

    const queryClient = useQueryClient();

    const removeFriendMutation = useMutation(
        ({ friendId }) => {
            // Perform the DELETE request to the API endpoint
            return fetch(`http://localhost:8080/api/friends/remove-friend/${userId}/${friendId}`, {
                method: 'DELETE',
            });
        },
        {
            // onSuccess callback is called on a successful DELETE request
            onSuccess: () => {
                console.log("success")
                // Invalidate queries that depend on this data
                queryClient.invalidateQueries('data2'); // Replace 'friends' with the key used for your friends list query
            },
            onError: error => {
                console.log("error")
            }
        }
    );

    const handleRemoveFriend = ( friendId) => {
        removeFriendMutation.mutate({ friendId });
    };

    const loading = "Loading...";
    const coloredLetters = loading.split("").map((letter, index) => (
        <span
            key={index}
            className={index % 2 === 0 ? "text-rose-500 text-4xl" : "text-blue-500 text-5xl"}
        >
            {letter}
        </span>
    ))


    if (isLoading)
        return <div className="flex">{coloredLetters}</div>;

    if (isError)
        return <div className="text-rose-500 text-4xl">Error fetching data</div>;

    // console.log(data.posts)
    // console.log(data)


    return (
        <div style={{
            color: "whitesmoke"
        }}>

            <main>

                <header>
                    <div className="tb">
                        <div className="td" id="logo">
                            <a href="#"><i className="fab fa-facebook-square"></i></a>
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
                        <img src="data:image/jpeg;base64,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" alt="Banner image"/>
                    </div>
                    <div id="profile-d">
                        <div id="profile-pic">
                            <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTYZxm98EmwJ1v1onwGLXl6sNqt857dqUmEOw&usqp=CAU"/>
                        </div>
                        <div style={{
                            color: "#00ffcc"
                        }}>
                            {userFirstName} {userLastName}
                        </div>

                    </div>
                    <div id="black-grd"></div>
                </div>
                <div id="main-content">
                    <div className="tb">
                        <div className="td" id="l-col">
                            <div className="l-cnt">
                                <div className="cnt-label">

                                    <span>Bio</span>
                                    <div className="lb-action"><i className="material-icons">edit</i></div>
                                </div>
                                <div id="i-box">
                                    <div id="u-occ">Don't be sad because sad backwards is das and das not good.</div>

                                </div>
                            </div>
                            <p className="vis"></p>
                            <div id="mj">

                            </div>

                            <div className="l-cnt l-mrg">
                                <div className="cnt-label">
                                    {/*<i className="l-i" id="l-i-p"></i>*/}
                                    <span><img style={{
                                        borderRadius: "50%",
                                        height:"75px"
                                    }} src="data:image/jpeg;base64,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"/></span>
                                    <span>Friends {data2.friends.length}</span>

                                    {data2.friends.map((post) => (
                                        <div key={post.id} className="postk">
                                            <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSY3-jXkdGt479-LGLdQMet805Y1lQlgH0CBg&usqp=CAU" alt={`Profile of ${post.username}`} className="profile-pic" />
                                            <div className="post-content">
                                                <i><img src="data:image/png;base64,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" style={{
                                                    width: "20px",
                                                    margin: "5px"
                                                }} className="hover:cursor-pointer" onClick={() => handleRemoveFriend(post.id)}/> </i>

                                                <Link to={`/profile/${post.id}`}>
                                                <p className="text-rose-500">{post.firstName}</p>
                                                </Link>
                                            </div>
                                        </div>
                                    ))}

                                </div>

                            </div>

                            <div id="t-box">
                                <a href="#">Privacy</a> <a href="#">Terms</a> <a href="#">Advertising</a> <a href="#">Ad Choices</a> <a href="#">Cookies</a> <span id="t-more">More<i className="material-icons">arrow_drop_down</i></span>
                                <div id="cpy-nt">Stacks Of Laughs &copy; <span id="curr-year"></span></div>
                            </div>
                        </div>
                        <div className="td" id="m-col">
                            <div className="m-mrg" id="p-tabs">
                                <div className="tb">
                                    <div className="td">
                                        <div className="tb" id="p-tabs-m">
                                            <div className="td active"><i className="material-icons">8</i><span>Posts</span></div>
                                            <div className="td"><i className="material-icons">Friends</i><span>1180</span></div>
                                            <div className="td"><i className="material-icons">Photos</i><span>23</span></div>
                                                <div className="td"><i className="material-icons">Followers</i><span>5471</span></div>
                                            <div className="td"><i className="material-icons">Following</i><span>2564</span></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="m-mrg" id="composer">
                                <div id="c-tabs-cvr">
                                    <div className="tb" id="c-tabs">
                                        <div className="td active"><span>Make Post</span></div>
                                        <div className="td"><i className="material-icons" >camera_enhance</i><span>Photo/Video</span></div>
                                        <div className="td"><i className="material-icons">videocam</i><span>Live Video</span></div>
                                        <div className="td"><i className="material-icons">event</i><span>Life Event</span></div>
                                    </div>
                                </div>
                                <div id="c-c-main">
                                    <div className="tb">
                                        <div className="td profile-pic" id="p-c-i"><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTYZxm98EmwJ1v1onwGLXl6sNqt857dqUmEOw&usqp=CAU" alt="Profile pic"/></div>
                                        <div className="td" id="c-inp">
                                            <input type="text" placeholder="What's on your mind?" onClick={openModal}/>


                                            <Modal isOpen={isModalOpen} onClose={closeModal}>
                                                {/*<input type="text"/>*/}
                                                <h2 className="text-center text-indigo-800">Status</h2>
                                                <input className="text-black" type="text" onChange={(e) => setPost(e.target.value)} style={{
                                                    width: "250px",
                                                    height: "110px",
                                                    borderRadius: "20px",
                                                    border: "1px"
                                                }}/>
                                                {/*<p>This is the content of the modal.</p>*/}
                                                <div className="m-5 text-center">
                                                    <button className="bg-indigo-600 text-white w-1/2 m-5 br hover:bg-blue-950" onClick={handleCreateItem} style={{
                                                        borderRadius: "30px"
                                                    }}>Post</button>
                                                    <button className="bg-red-600 text-white w-1/2 hover:bg-orange-500" onClick={closeModal} style={{
                                                        borderRadius: "30px"
                                                    }}>Cancel</button>
                                                </div>
                                                {postResult && (
                                                    <div
                                                        className={`alert mt-2 ${
                                                            postResult === "Success" ? "text-green-500" : "text-rose-500"
                                                        }`}
                                                        role="alert"
                                                    >
                                                        <pre>{postResult}</pre>
                                                    </div>
                                                )}
                                            </Modal>


                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <div className="post-list">
                                    {data.posts.map((post) => (
                                        <div key={post.id} className="postk mb-3">
                                            <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTYZxm98EmwJ1v1onwGLXl6sNqt857dqUmEOw&usqp=CAU" alt={`Profile of ${post.username}`} className="profile-pic" />
                                            <div className="post-content">
                                                <p className="username text-sm text-gray-400">
                                                    {/*{data.firstName} */}
                                                   You updated your status</p>
                                                <p className="post-text text-black">{post.content}</p>
                                            </div>
                                        </div>
                                    ))}
                                </div>

                                <div className="post">
                                    <div className="tb">
                                        <a href="#" className="td p-p-pic"><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTYZxm98EmwJ1v1onwGLXl6sNqt857dqUmEOw&usqp=CAU"/></a>
                                        <div className="td p-r-hdr">
                                            <div className="p-u-info" style={{
                                                color:"#a8a8a8"
                                            }}>
                                                <a href="#" style={{
                                                    color:"#f1861b"
                                                }}>You</a> shared a picture
                                            </div>
                                            <div className="p-dt">
                                                <span>25 minutes ago</span>
                                            </div>
                                        </div>
                                    </div>
                                        <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTLG7lMAY-9DxXPHoKbjHI2sDhQIcOwdm0kog&usqp=CAU"/>

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
export default Profile;
