import React, {useEffect, useRef, useState} from 'react';
import { Container} from 'react-bootstrap';
import {motion} from 'framer-motion';
import {images} from "./people";

const Carousel = () =>  {


    // const [width, setWidth] = useState(0);
    const carousel = useRef();
    // useEffect(() => {
    //     console.log(carousel.current.scrollWidth, carousel.current.offsetWidth)
    //     setWidth(carousel.current.scrollWidth - carousel.current.offsetWidth)
    //
    // },[])

    return (
        <Container className="py-4">
            <motion.div ref={carousel} className="carousel" whileTap={{cursor:"grabbing"}} >

                <motion.div  drag="x"  dragConstraints={{
                    right: -500,
                    left: -1100

                }} className="inner-carousel">

                    {images.map(image => {
                        return(

                            <motion.div className="item" key={image}>

                                <img src={image} alt="" style={{
                                    boxShadow:"0 0 10px rgba(0, 0, 255, 0.5)"
                                }}/>

                            </motion.div>
                        )
                    })}

                </motion.div>

            </motion.div>
        </Container>
    )
}
export default Carousel
