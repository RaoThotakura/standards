import { useState, useRef } from 'react';

/**Scrolling an image carousel */

export default function CatFriends() {
  const [index, setIndex] = useState(0);
  const imageRefs = useRef ([]);
  return (
    <>
      <nav>
        <button onClick={() => {
          if (index < catList.length - 1) {
            setIndex(index + 1);
            imageRefs.current[index].scrollIntoView({
              behavior: 'smooth',
              block: 'nearest',
              inline: 'center'
            });
            
          } else {
            setIndex(0);
            
            imageRefs.current[0].scrollIntoView({
              behavior: 'smooth',
              block: 'nearest',
              inline: 'center'
            });
          }
        }}>
          Next
        </button>
      </nav>
      
      <div>
        <ul>
          {catList.map((cat, i) => (
            <li key={cat.id}>
              <img
                className={
                  index === i ?
                    'active' :
                    ''
                }
                src={cat.imageUrl}
                alt={'Cat #' + cat.id}
                ref={el => (imageRefs.current[i] = el)} 
              />
            </li>
          ))}
        </ul>
      </div>
    </>
  );
}

const catCount = 10;
const catList = new Array(catCount);
for (let i = 0; i < catCount; i++) {
  const bucket = Math.floor(Math.random() * catCount) % 2;
  let imageUrl = '';
  switch (bucket) {
    case 0: {
      imageUrl = "https://placecats.com/neo/250/200";
      break;
    }
    case 1: {
      imageUrl = "https://placecats.com/millie/250/200";
      break;
    }
    case 2:
    default: {
      imageUrl = "https://placecats.com/bella/250/200";
      break;
    }
  }
  catList[i] = {
    id: i,
    imageUrl,
  };
}



/** Play and pause the video */

import { useState, useRef } from 'react';

export default function VideoPlayer() {
  const [isPlaying, setIsPlaying] = useState(false);
  const videoRef = useRef (null);

  function handleClick() {
    const nextIsPlaying = !isPlaying;
    setIsPlaying(nextIsPlaying);

    !isPlaying && videoRef.current.play();
    isPlaying && videoRef.current.pause();

    
  }

  return (
    <>
      <button onClick={handleClick}>
        {isPlaying ? 'Pause' : 'Play'}
      </button>
      <video width="250" ref={videoRef}>
        <source
          src="https://interactive-examples.mdn.mozilla.net/media/cc0-videos/flower.mp4"
          type="video/mp4"
        />
      </video>
    </>
  )
}

/** Focus the search field with separate components */

/** App.js */

import SearchButton from './SearchButton.js';
import SearchInput from './SearchInput.js';
import { useRef } from 'react';

export default function Page() {

    const inputRef = useRef(null);
    function handleClick() {
      inputRef.current.focus();
    }
  
  return (
    <>
      <nav>
        <SearchButton onClick={handleClick}/>
      </nav>
      <SearchInput ref={inputRef}/>
    </>
  );
}


/** SearchButton.js */


export default function SearchButton({onClick}) {
    return (
      <button onClick={onClick}>
        Search
      </button>
    );
}

/**SearchInput.js */

export default function SearchInput({ref}) {
  return (
    <input
      placeholder="Looking for something?"
      ref={ref}
    />
  );
}
