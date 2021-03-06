import React from 'react';
import './Header.css';
import PropTypes from 'prop-types';
import { MdChevronRight, MdChevronLeft, MdAccountCircle } from 'react-icons/lib/md';
import { Popover, PopoverHeader, PopoverBody } from 'reactstrap';
import UserIcon from '../../resources/img/user.svg';

const Header = ({ onClick, popover, user }) => {
    let firstName='';
    let secondName='';
    let email='';
    let sfCnt='';
    if(user){
        user=user.toJS();
        firstName=user.firstName;
        secondName=user.secondName;
        email=user.email;
        sfCnt=user.sfCnt;
    }
    return (
        <nav className="absolute-top navbar navbar-absolute navbar-expand-lg
         nopadding-top nopadding-bottom"
            id="Header">
            <div className="Header-container">
                <div className="Header-box-left">
                    <span className="main-header-text">
                        <span className="main-header-text bold">P
                        </span>alm <span className="main-header-text bold">T</span>o
                        <span className="main-header-text bold"> F</span>arm
                    </span>
                </div>
                <div className="Header-box-right">
                    <span className="main-header-username size-7" id="nanum-gothic">
                        {firstName} {secondName}
                    </span>
                    <a className="Header-toggle" onClick={onClick[0]} id="Popover1">
                        <img src={UserIcon}/>
                    </a>
                    <Popover placement="bottom" isOpen={popover} target="Popover1" toggle={onClick[0]}>
                        <PopoverHeader>
                            <span id="nanum-gothic">{email}</span>
                        </PopoverHeader>
                        <PopoverBody>
                            <div>
                                <div id="nanum-gothic">{sfCnt}대의 재배기 보유</div>
                            </div>
                        </PopoverBody>
                    </Popover>
                </div>
            </div>
        </nav>
    )
};

Header.propTypes = {
    onClick: PropTypes.array.isRequired,
    popover: PropTypes.bool.isRequired,
}

export default Header;