import React, {useState} from 'react';
import axios from "axios";
import {useQuery} from "react-query";
import Modal from "../misc/Modal"
import URL from "../misc/url";



const Upload = () => {
    const [selectedImage, setSelectedImage] = useState(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [message, setMessage] = useState("");


    const openModal = () => setIsModalOpen(true);

    const closeModal = () => {
        setIsModalOpen(false);

    }


    const handleImageChange = (e) => {
        const file = e.target.files[0];
        setSelectedImage(file);
    };

    const handleImageUpload = async (e) => {
        e.preventDefault();
        const formData = new FormData();
        formData.append("file", selectedImage);

        try {
            await axios.post(`${URL.BASE_URL}/api/image/upload`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });
            console.log("upload success")
            // Handle a successful upload, such as displaying a success message to the user.
        } catch (error) {
            console.log("failed to upload " + error)
            // Handle errors, such as showing an error message to the user.
        }
    }

    const replaceImageUpload = async (e) => {
        e.preventDefault();
        const formData = new FormData();
        formData.append("file", selectedImage);

        try {
            await axios.put(`${URL.BASE_URL}/api/image/update/1`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });
            console.log("replace successful")
            setMessage("Profile picture updated")

            setTimeout(() => {
               setMessage("");
                closeModal()
            }, 2000)
        } catch (error) {
            console.log("failed to replace " + error)
            setMessage("Failed to upload");

            setTimeout(() => {
            setMessage("");
                closeModal()
            }, 2000)
        }
    }

    const fetchImage = async () => {
        try {
            // if (!response.ok) {
            //     console.log(response)
            //     throw new Error('Network response was not ok');
            // }
            // console.log(response)
            // console.log(response.url)
            return await fetch(`http://localhost:8080/api/image/display/1`)
        } catch (error) {
            console.log(error)
            throw new Error('Error fetching data');
        }
    };

    const { data, isLoading, isError } = useQuery("myImage", fetchImage);

    if (isLoading)
        return <div className="flex">Loading...</div>;

    if (isError)
        return <div className="text-rose-500 text-4xl">Error fetching data</div>;


    return (
        <div className="text-white">
{/*<span>*/}
            <i>
            <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQJRG0ehYH1QqNyo6TwX1-eLmFP-qK-qQDUDg&usqp=CAU" style={{
                height: "20px",
                width: "40px"
            }} onClick={openModal} className="hover:cursor-pointer"/>
            </i>
    <Modal isOpen={isModalOpen} onClose={closeModal}>
           <form className="text-red-800 m-5">
                <input type="file" accept="image/*" onChange={handleImageChange} />
                <button onClick={replaceImageUpload} className="" style={{
                    marginRight: "80px"

                }}>Update Picture</button>
               <button type="button" onClick={closeModal}>Cancel</button>
            </form>
        <span className="text-cyan-950 text-center">{message}</span>

                       {/*<button className="text-red-800" onClick={closeModal}>Cancel</button>*/}


    </Modal>
{/*</span>*/}

            <form>
            <input type="file" accept="image/*" onChange={handleImageChange} />
            <button onClick={handleImageUpload}>Upload</button>
            </form>



            {/*<form>*/}
            {/*    <input type="file" accept="image/*" onChange={handleImageChange} />*/}
            {/*    <button onClick={replaceImageUpload}>Replace</button>*/}
            {/*</form>*/}
            <img style={{
                maxWidth: "100px"
            }} src={data.url}/>
            {/*{selectedImage && (*/}
            {/*    <img*/}
            {/*        src={URL.createObjectURL(selectedImage)}*/}
            {/*        alt="Selected"*/}
            {/*        style={{ maxWidth: '100px' }}*/}
            {/*    />*/}
            {/*)}*/}
        </div>
    );
}

export default Upload;
