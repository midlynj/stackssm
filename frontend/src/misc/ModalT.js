import Modal from "./Modal";
import {useEffect, useState} from "react";
import {useMutation} from "react-query";
import axios from "axios";
import {useSelector} from "react-redux";

const ModalT = () => {
    const [isModalOpen, setIsModalOpen] = useState(false);

    const openModal = () => setIsModalOpen(true);

    // const closeModal = () => {
    //     setIsModalOpen(false);
    //
    // }

    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0'); // Month is zero-based, so add 1 and pad with 0 if needed
    const day = String(today.getDate()).padStart(2, '0'); // Pad day with 0 if needed

    const formattedDate = `${year}-${month}-${day}`;
    const [post, setPost] = useState("");
    const [createdAt] = useState(formattedDate);

    const [postResult, setPostResult] = useState(null);

    const userId = useSelector((state) => state.persistedReducer.user.id);


    const closeModal = () => {
        setIsModalOpen(false);
        setPostResult("")

    }

    const { isLoading: isUpdatingStatus, mutate: postStatus } = useMutation(
        async () => {

            if (post.length < 1) {
                setPostResult("Status must not be empty")
                return
            }

            return await axios.post(`http://localhost:8080/api/posts/${userId}/new-post`, {
                content: post,
                createdAt: createdAt,
            });
        },
        {
            onSuccess: (res) => {
                if (res === undefined)
                    return

                const result =
                    "Success"

                setPostResult(result);

                setTimeout(() => {
                    setPostResult("")
                    setPost("")
                    // setOpen(false)
                    closeModal()

                }, 2000)
            },
            onError: (err) => {
                setPostResult("Error");
            },
        }
    );

    useEffect(() => {
        if (isUpdatingStatus) setPostResult("posting...");
    }, [isUpdatingStatus]);

    function postData() {
        try {
            postStatus();

        } catch (err) {
            setPostResult(err);
        }
    }

    return (
        <div className="a bg-amber-50">
            {/*<button onClick={openModal}>Open Modal</button>*/}
            <input type="text" placeholder="Update Status"  onClick={openModal}/>

            <Modal isOpen={isModalOpen} onClose={closeModal}>
                {/*<input type="text"/>*/}
                <h2 className="text-center text-indigo-800">Status</h2>
                <input type="text" onChange={(e) => setPost(e.target.value)} style={{
                    width: "250px",
                    height: "110px",
                    borderRadius: "20px"
                }}/>
                {/*<p>This is the content of the modal.</p>*/}
                <div className="m-5 text-center">
                <button className="bg-indigo-600 text-white w-1/2 m-5 br hover:bg-blue-950" onClick={postData} style={{
                    borderRadius: "30px"
                }}>Post</button>
                <button className="bg-red-600 text-white w-1/2 hover:bg-orange-500" onClick={closeModal} style={{
                    borderRadius: "30px"
                }}>Close</button>
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
    );

}
export default ModalT;
