import {Sidebar} from 'primereact/sidebar';
import {useState} from 'react';
import {Button} from 'primereact/button';
import {Ripple} from 'primereact/ripple';
import logo from '../../../assets/images/ot-logo.png';
import {Divider} from 'primereact/divider';
import {Icon} from '../Icon.tsx';
import {Link, useLocation, useNavigate} from "react-router-dom";


export const Navbar = () => {

    const [menuVisivel, setMenuVisivel] = useState(false);

    const location = useLocation();
    const navigate = useNavigate();
    const [menuAtivo, setMenuAtivo] = useState(`/${location.pathname.split("/")[1]}`);

    const menus = [
        {label: 'Equipamentos', url: '/equipamentos', icon: <Icon icon='laptop_chromebook' className='mr-2'/>, permissao: ["ADMINISTRAR_EQUIPAMENTOS"]},
        {label: 'Tecnologias', url: '/tecnologias', icon: <Icon icon='developer_board' className='mr-2'/>, permissao: ["ADMINISTRAR_TECNOLOGIAS"]},
        {label: 'Configurações Financeiras', url: '/configuracaoPagamentos', icon: <Icon icon='credit_card_gear' className='mr-2'/>, permissao: ["ADMINISTRAR_CONFIGURACOES_FINANCEIRAS"]},
        {label: 'Bancos', url: '/bancos', icon: <Icon icon='account_balance' className='mr-2'/>, permissao: ["ADMINISTRAR_BANCOS"]},
        {label: 'Documentos', url: '/documentosContratacao', icon: <Icon icon='difference' className='mr-2'/>, permissao: ["ADMINISTRAR_DOCUMENTOS"]},
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
                    
                    <div className="flex align-items-center gap-2">
                        
                        <Button 
                            icon={<Icon icon="notifications_active" size={25}/>}
                            text rounded severity="secondary"
                            onClick={() => {
                                setMenuVisivel(false);
                            }}
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
                                    <div
                                        className="flex align-items-center justify-content-between px-4 pt-3 flex-shrink-0">
                                        <span className="inline-flex align-items-center gap-2">
                                            <img alt="logo" src={logo} height="55" className="mr-2"/>
                                        </span>
                                        <span>
                                            <Button type="button" onClick={() => setMenuVisivel(false)}
                                                    icon={<Icon icon="close"/>}
                                                    rounded severity="secondary" text
                                                    className="h-2rem w-2rem"
                                            />
                                        </span>
                                    </div>
                                    <Divider className='mb-0'/>
                                    <div className="overflow-y-auto">
                                        <ul className="list-none p-3 m-0">
                                            {menus.map((item, index) => (
                                        
                                                <li key={index}
                                                    onClick={() => {
                                                        setMenuAtivo(item.url);
                                                        navigate(item.url);
                                                        setMenuVisivel(false);
                                                    }}
                                                >
                                                    <span
                                                        className={`${menuAtivo === item.url ? 'bg-blue-600 text-white' : 'hover:surface-100'} p-ripple flex align-items-center cursor-pointer p-3 border-round text-700 transition-duration-150 transition-colors w-full`}
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