import React from "react";
import "./postlist.css";
import {useQuery} from "react-query";
import URL from "./url"
import axios from "axios";
import Loading from "../component/Loading";
import Error from "../component/Error";
const PostList = () => {
    const handleFetchAllPosts = async () => {
        try {
            const response = await axios.get(URL.FETCH_ALL_POSTS);
            return response.data;

        } catch (error) {
            throw new Error(`Error fetching data: ${error.message}`);
        }
    };

    const { data, isLoading, isError } = useQuery("fetchAllPosts", handleFetchAllPosts);

    if (isLoading)
        return <div><Loading/></div>;

    if (isError)
        return <div><Error/></div>;

    return (
        <div className="post-list">
            {data.map((post) => (
                <div key={post.id} className="postk">
                    <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTYZxm98EmwJ1v1onwGLXl6sNqt857dqUmEOw&usqp=CAU" alt={`Profile of ${post.username}`} className="profile-pic" />
                    <div className="post-content">
                        <p className="username text-sm text-gray-400">{post.author.firstName} updated their status</p>
                        <p className="post-text">{post.content}</p>
                    </div>
                </div>
            ))}
        </div>
    );
};

export default PostList;
