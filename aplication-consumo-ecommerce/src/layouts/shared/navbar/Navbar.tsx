import {Sidebar} from 'primereact/sidebar';
import {useState} from 'react';
import {Button} from 'primereact/button';
import {Ripple} from 'primereact/ripple';
import {Icon} from '../Icon.tsx';
import {useLocation, useNavigate} from "react-router-dom";


export const Navbar = () => {

    const [menuVisivel, setMenuVisivel] = useState(false);

    const location = useLocation();
    const navigate = useNavigate();

    const menus = [
        { label: 'DashBoard', url: '/', icon: <Icon icon='dashboard' className='mr-2' /> },
        { label: 'Vendas', url: '/vendas', icon: <Icon icon='shopping_cart' className='mr-2' /> },
        { label: 'Anúncios', url: '/anuncios', icon: <Icon icon='sell' className='mr-2' /> },
    ];

    
    return (
        <>
            <div className="card shadow-1 mb-3">
                <div
                    className='flex flex-row justify-content-between align-items-center px-3 border-bottom-1 surface-border'>
                    <div className="flex align-items-center">
                        <Button type="button" onClick={() => setMenuVisivel(true)}
                                icon={<Icon icon="menu"/>}
                                rounded text severity="secondary"
                                className="h-2rem w-2rem"
                        />
                    </div>
                </div>
            </div>
            

            <div className="card flex justify-content-center">
                <Sidebar
                    visible={menuVisivel}
                    onHide={() => setMenuVisivel(false)}
                    content={() => (
                        <div className="min-h-screen flex relative lg:static surface-ground">
                            <div id="app-sidebar-2"
                                 className="surface-section h-screen block flex-shrink-0 absolute lg:static left-0 top-0 z-1 surface-border select-none"
                                 style={{width: '100%'}}>
                                <div className="flex flex-column h-full">
                                    <div className="overflow-y-auto">
                                        <ul className="list-none p-3 m-0">
                                            {menus.map((item, index) => (
                                        
                                                <li key={index}
                                                    onClick={() => {
                                                        navigate(item.url);
                                                        setMenuVisivel(false);
                                                    }}
                                                >
                                                    <span
                                                        className={`${location.pathname === item.url ? 'bg-blue-600 text-white' : 'hover:surface-100'} p-ripple flex align-items-center cursor-pointer p-3 border-round text-700 transition-duration-150 transition-colors w-full`}
                                                    >
                                                        {item.icon}
                                                        <span className="font-medium">{item.label}</span>
                                                        <Ripple/>
                                                    </span>
                                                </li>
                                            ))}
                                        </ul>
                                    </div>
                                    <div
                                        className="mt-auto flex justify-content-center border-top-1 surface-border p-2">
                                    </div>
                                </div>
                            </div>
                        </div>
                    )}
                ></Sidebar>
            </div>
        </>

    )
}