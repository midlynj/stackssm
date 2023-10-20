import {Link, useParams} from 'react-router-dom';
import {useQuery} from "react-query";
import axios from "axios";
import React, {useEffect, useState} from "react";
import {useSelector} from "react-redux";

const FriendProfile = () => {
    const {id} = useParams();
    const {userData, setUserData} = useState();


    const userId = useSelector((state) => state.persistedReducer.user.id);

    const { data, isLoading, isError } = useQuery("friendProfile", () =>
        axios.get(`http://localhost:8080/api/users/${id}`).then((response) => response.data)
    );
    console.log(data)

    useEffect(() => {
        // Load user data when the userId changes
    }, [id]);



    if (isLoading)
        return <div className="flex">Loading...</div>;

    if (isError)
        return <div className="text-rose-500 text-4xl">Error fetching data</div>;

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
                        }}>{data.firstName} {data.lastName}</div>

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
                            <div className="l-cnt l-mrg">
                                <div className="cnt-label">
                                    <i className="l-i" id="l-i-p"></i>
                                    <span>Friends {data.friends.length}</span>

                                    {data.friends.map((post) => (
                                        <div key={post.id} className="postk" style={{
                                            padding: "0px"
                                        }}>
                                            <div className={`post-text text-black ${userId === post.id ? "hidden" : "text-rose-500"}`}>
                                            <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTYZxm98EmwJ1v1onwGLXl6sNqt857dqUmEOw&usqp=CAU" alt={`Profile of ${post.username}`} className="profile-pic" />
                                            <div className="post-content">
                                                {/*<p className="username text-sm text-gray-400">{post.author.firstName} updated their status</p>*/}
                                               {/*<p className="text-black">{post.firstName}</p>*/}

                                                {/*<Link to={`/profile/${post.id}`}>*/}
                                                {/*    <p className="post-text text-black">{post.id}</p>*/}
                                                {/*</Link>*/}
                                                <Link to={`/profile/${post.id}`}>
                                                    <p className={`post-text text-black ${userId === post.id ? "hidden" : "text-rose-500"}`}>{post.firstName}</p>
                                                </Link>
                                            </div>
                                            </div>
                                        </div>
                                    ))}

                                </div>
                                <div id="photos">
                                    <div className="tb">
                                        <div className="tr">
                                            <div className="td"></div>
                                            <div className="td"></div>
                                            <div className="td"></div>
                                        </div>
                                        <div className="tr">
                                            <div className="td"></div>
                                            <div className="td"></div>
                                            <div className="td"></div>
                                        </div>
                                        <div className="tr">
                                            <div className="td"></div>
                                            <div className="td"></div>
                                            <div className="td"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div id="t-box">
                                <a href="#">Privacy</a> <a href="#">Terms</a> <a href="#">Advertising</a> <a href="#">Ad Choices</a> <a href="#">Cookies</a> <span id="t-more">More<i className="material-icons">arrow_drop_down</i></span>
                                <div id="cpy-nt">Facebook &copy; <span id="curr-year"></span></div>
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
                                                   // onClick={openModal}
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
                                        <a href="#" className="td p-p-pic"><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTYZxm98EmwJ1v1onwGLXl6sNqt857dqUmEOw&usqp=CAU" alt="Rajeev's profile pic"/></a>
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
    )


}
export default FriendProfile;
