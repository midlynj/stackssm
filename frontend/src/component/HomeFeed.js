import {useState} from "react";
import Carousel from "../misc/Carousel";
import PostList from "../misc/PostList";
import {useSelector} from "react-redux";
import axios from "axios";
import {useMutation, useQueryClient} from "react-query";
import Modal from "../misc/Modal";
import {DATE} from "../misc/date";
import URL from "../misc/url";

const HomeFeed = () => {
    const userId = useSelector((state) => state.persistedReducer.user.id);
    const queryClient = useQueryClient();

    const [isModalOpen, setIsModalOpen] = useState(false);
    const [post, setPost] = useState("");
    const [postResult, setPostResult] = useState(null);

    const openModal = () => setIsModalOpen(true);
    const closeModal = () => {
        setIsModalOpen(false);
        setPostResult("");
    }

    const createStatusMutation = useMutation(
        async () => {

            return await axios.post(`${URL.BASE_URL}/posts/${userId}/new-post`, {
                content: post,
                createdAt: DATE
            });
        },
        {
            onSuccess: (response) => {
                setPostResult("Success");

                queryClient.invalidateQueries("fetchAllPosts");

                closeModal();
                setPostResult("");
                setPost("");
            },
            onError: (err) => {
                setPostResult("Error creating post");
            },
        }
    );

    const handleCreateNewStatus = ( ) => {
        createStatusMutation.mutate();
    };

    return (
        <div>
            <div className="text-violet-800 text-2xl" style={{
                textAlign:"center"
            }}>People You May Know
            <Carousel/>
            </div>
            <main>

                <header>
                    <div className="tb">

                        <div className="td" id="i-links">
                            <div className="tb">
                                <div className="td" id="m-td">

                                </div>

                            </div>
                        </div>
                    </div>
                </header>

                <div id="main-content">
                    <div className="tb">
                        <div className="td" id="l-col">

                        </div>
                        <div className="td" id="m-col">

                            <div className="m-mrg" id="composer">
                                <div id="c-tabs-cvr">
                                    <div className="tb" id="c-tabs">
                                        <div className="td active"><span>Update Status</span></div>
                                        <div className="td"><i className="material-icons" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</i><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></div>
                                        <div className="td"><i className="material-icons">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</i><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></div>
                                        <div className="td"><i className="material-icons">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</i><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></div>
                                    </div>
                                </div>
                                <div id="c-c-main">
                                    <div className="tb">
                                        <div className="td profile-pic" id="p-c-i"><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTYZxm98EmwJ1v1onwGLXl6sNqt857dqUmEOw&usqp=CAU" alt="Profile pic"/></div>
                                        <div className="td" id="c-inp">
                                            <input type="text" placeholder="What's on your mind?"
                                                onClick={openModal}
                                            />

                                            <Modal isOpen={isModalOpen} onClose={closeModal}>
                                                <h2 className="text-center text-indigo-800">Status</h2>
                                                <input type="text" onChange={(e) => setPost(e.target.value)} style={{
                                                    width: "250px",
                                                    height: "110px",
                                                    borderRadius: "20px",
                                                    border: "1px"
                                                }}/>
                                                <div className="m-5 text-center">
                                                    <button className="bg-indigo-600 text-white w-1/2 m-5 br hover:bg-blue-950" onClick={handleCreateNewStatus} style={{
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
                            <PostList/>
                            <div>
                                <div className="post">

                                    <div>

                                    </div>
                                    <div className="tb" style={{
                                        marginTop: "10px"
                                    }}>
                                        {/*<a href="#" className="td p-p-pic"><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTYZxm98EmwJ1v1onwGLXl6sNqt857dqUmEOw&usqp=CAU" alt="profile pic"/></a>*/}
                                        <div className="td p-r-hdr">
                                            <div className="p-u-info" style={{
                                                color:"#a8a8a8"
                                            }}>
                                                {/*<a href="#" style={{*/}
                                                {/*    color:"#f1861b"*/}
                                                {/*}}>Joey Stacks</a> shared a picture*/}
                                            </div>
                                            <div className="p-dt">
                                                <span>25 minutes ago</span>
                                            </div>
                                        </div>
                                    </div>
                                    <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTLG7lMAY-9DxXPHoKbjHI2sDhQIcOwdm0kog&usqp=CAU" alt="tokoyo"/>

                                    <div>

                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
                <div id="device-bar-2"><i className="fab fa-apple"></i></div>
            </main>

        </div>

    );
}
export default HomeFeed;
